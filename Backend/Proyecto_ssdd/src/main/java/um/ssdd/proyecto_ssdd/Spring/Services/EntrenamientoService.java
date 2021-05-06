package um.ssdd.proyecto_ssdd.Spring.Services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Collection;
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

import com.google.common.io.Files;

import um.ssdd.proyecto_ssdd.Spring.Entities.Entrenamiento;
import um.ssdd.proyecto_ssdd.Spring.Entities.Fichero;
import um.ssdd.proyecto_ssdd.Spring.Entities.Usuario;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IEntrenamientoRepository;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IFicheroRepository;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IUsuarioRepository;
import um.ssdd.proyecto_ssdd.Spring.Services.DTOs.EntrenamientoDTO;
import um.ssdd.proyecto_ssdd.Spring.Services.DTOs.PalabrasDTO;


@Service
public class EntrenamientoService {
	
	@Autowired
	private IEntrenamientoRepository entrenamientoRepository;
	
	@Autowired
	private IFicheroRepository ficheroRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	public ModelMapper modelMapper;
	
	public List<EntrenamientoDTO> getAll(String id) {
		
		
		Usuario u = usuarioRepository.findById(id).orElse(null);
		
		if (u != null) {
					
			
			u.addPeticionSoporteFront();
			usuarioRepository.save(u);
			
			if (u.getAllEntrenamientos().size() > 0)
				return u.getAllEntrenamientos().stream().map(this::entityToResponse).collect(Collectors.toList());
		}
		
		return null;
		
		
		
	}
	
	
	private EntrenamientoDTO entityToResponse(Entrenamiento entrenamiento) {		
		return modelMapper.map(entrenamiento, EntrenamientoDTO.class);
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
		        
		        SecureRandom random = new SecureRandom();
				int num = random.nextInt(100000);
				String tid = String.format("%05d", num);
				fichero.setTID(tid);
				
				
				Usuario u = usuarioRepository.findById(id).orElse(null);
				
				

				
				fichero.setEntrenamiento(entrenamientoRepository.save(entrenamiento));
				ficheroRepository.save(fichero);
				
				u.modificarFichero(fichero);
				usuarioRepository.save(u);
				
		        try {
					return new URI(tid);
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}		
			}
		}
		return null;
	}


	public PalabrasDTO entrenado(String wid, String word) {
		
		Entrenamiento entrenamiento = entrenamientoRepository.findById(wid).orElse(null);
		
		if (entrenamiento != null) {
			
			try {
				Files.write(entrenamiento.getEntrenamiento().getData(), new File("pathToWriteto.bin"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Word2Vec vec = WordVectorSerializer.readWord2VecModel("pathToWriteto.bin");
			
		    try {
		    	
		    	Collection<String> lst = vec.wordsNearestSum(word, 10);
		    	
		    	
		        return new PalabrasDTO(lst.toString()); 
		
		    } catch (NullPointerException e) {}
			
		}
		
		
	    
	    return new PalabrasDTO("");
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
        
        return new Entrenamiento(fichero.getFileName(), new Binary(BsonBinarySubType.BINARY, out.toByteArray()));
        
		
	}
	

}