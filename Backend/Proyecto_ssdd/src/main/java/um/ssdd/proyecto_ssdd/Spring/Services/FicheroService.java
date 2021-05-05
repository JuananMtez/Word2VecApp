package um.ssdd.proyecto_ssdd.Spring.Services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import um.ssdd.proyecto_ssdd.Spring.Entities.Entrenamiento;
import um.ssdd.proyecto_ssdd.Spring.Entities.Fichero;
import um.ssdd.proyecto_ssdd.Spring.Entities.Usuario;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IEntrenamientoRepository;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IFicheroRepository;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IUsuarioRepository;
import um.ssdd.proyecto_ssdd.Spring.Services.DTOs.FicheroDTO;


@Service
public class FicheroService {
	
	
	@Autowired 
	private IFicheroRepository ficheroRepository;
	
	@Autowired 
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IEntrenamientoRepository entrenamientoRepository;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public List<FicheroDTO> getAll() {
		
		return ficheroRepository.findAll().stream().map(this::entityToResponse).collect(Collectors.toList());
	}
	
	public List<FicheroDTO> getAllUsuario(String id) {
		
		Usuario u = usuarioRepository.findById(id).orElse(null);
		u.addPeticionSoporteFront();
		usuarioRepository.save(u);
		
		
		
		
		return ficheroRepository.findByUserId(id).stream().map(this::entityToResponse).collect(Collectors.toList());

	}
	

	public FicheroDTO get(String id) {
		
		
		Fichero f = ficheroRepository.findById(id).orElse(null);

		if (f != null) {
			return entityToResponse(f);
		}
		return null;
	}

	public URI post(String id, MultipartFile file) {
	
		
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		Fichero f = ficheroRepository.findByUserIdAndFileName(id, file.getOriginalFilename());
		
		
		
		if (usuario != null && f == null) {
		
			Fichero fichero = new Fichero(id, file.getOriginalFilename());
			
			try {
				
				fichero.setFichero(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
				fichero = ficheroRepository.insert(fichero);
				
				usuario.addFichero(fichero);
				usuario.addPeticionSoporteFront();
				usuario.addFicherosActuales();
				usuario.addTotalFicherosSubidos();
				
				usuarioRepository.save(usuario);
				
				
				
				URI uri = new URI(fichero.getFID());
				return uri;
	
			} catch (IOException | URISyntaxException e) {
				return null;
			}
		}
		
		return null;
	}

	public boolean delete(String id, String fid) {

		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		if (ficheroRepository.findByUserIdAndFid(id, fid) != null && usuario != null) {

			
			ficheroRepository.deleteById(fid);
			
			usuario.deleteFichero(fid);
			usuario.addPeticionSoporteFront();
			usuario.removeFicherosActuales();
			
			usuarioRepository.save(usuario);
			
			return true;
		}
		return false;
	}
	
	public String getText(String id, String fid) {

		Usuario u = usuarioRepository.findById(id).orElse(null);
		u.addPeticionSoporteFront();
		usuarioRepository.save(u);
		
		Fichero fichero = ficheroRepository.findByUserIdAndFid(id, fid);
		if (fichero != null) {
			fichero.sumarLectura();
			ficheroRepository.save(fichero);
			return new String(fichero.getFichero().getData(), Charset.forName("UTF-8"));
		}
		return null;
		
	}
	
	
	public URI entrenar(String id, String fid) {
		
		
		Fichero fichero = ficheroRepository.findByUserIdAndFid(id, fid);
		
		if (fichero != null) {
			
			if (fichero.getTID().equals("") || fichero.getEntrenamiento() == null) {
				
				
				Entrenamiento entrenamiento = null;
				try {
					entrenamiento = word2vecTrain(fichero);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        fichero.setEntrenamiento(entrenamiento);
		        
		        SecureRandom random = new SecureRandom();
				int num = random.nextInt(100000);
				
				
				ficheroRepository.save(fichero);
				
				entrenamientoRepository.save(entrenamiento);
				
				
			
		        
		        try {
					return new URI(String.format("%05d", num));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				
			}
			
		}
		
		return null;
		
		
		
		
		
		
		
		
	}
	
	public boolean fileNameRepeated(String id, MultipartFile file) {
		return ficheroRepository.findByUserIdAndFileName(id, file.getOriginalFilename()) != null;
	}

	private FicheroDTO entityToResponse(Fichero fichero) {		
		return modelMapper.map(fichero, FicheroDTO.class);
	}
	
	
	
	private Entrenamiento word2vecTrain(Fichero fichero) throws IOException {
		
		SentenceIterator iter = new BasicLineIterator(new ByteArrayInputStream(fichero.getFichero().getData()));
        TokenizerFactory t = new DefaultTokenizerFactory();

        t.setTokenPreProcessor(new CommonPreprocessor());

        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(5)
                .iterations(1)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        vec.fit();


        ByteArrayOutputStream out = new ByteArrayOutputStream(100*1024);
        
        WordVectorSerializer.writeWord2VecModel(vec, out);
        
        return new Entrenamiento(new Binary(BsonBinarySubType.BINARY, out.toByteArray()));
        
		
	}
	
}
