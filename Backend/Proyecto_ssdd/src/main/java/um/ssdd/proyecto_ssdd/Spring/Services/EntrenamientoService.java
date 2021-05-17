package um.ssdd.proyecto_ssdd.Spring.Services;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.io.Files;

import um.ssdd.proyecto_ssdd.DTOs.EntrenamientoDTO;
import um.ssdd.proyecto_ssdd.DTOs.PalabrasDTO;
import um.ssdd.proyecto_ssdd.Entities.Entrenamiento;
import um.ssdd.proyecto_ssdd.Entities.Fichero;
import um.ssdd.proyecto_ssdd.Entities.Usuario;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IEntrenamientoRepository;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IFicheroRepository;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IUsuarioRepository;
import um.ssdd.proyecto_ssdd.grpc.entrenamiento.apps.ClienteEntrenamiento;


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
			
			List<Fichero> ficheros = ficheroRepository.findByUserId(u.getId());
			if (ficheros.size() > 0)
				return ficheros.stream().filter(f->f.getEntrenamiento() != null).map(f->entityToResponse(f.getEntrenamiento())).collect(Collectors.toList());
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
				
				
				/*Entrenamiento entrenamiento = null;
				try {
					entrenamiento = word2vecTrain(fichero);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				SecureRandom random = new SecureRandom();
				int num = random.nextInt(100000000);
				String tid = String.format("%08d", num);
				fichero.setTID(tid);
				ficheroRepository.save(fichero);
				
				try {
					ClienteEntrenamiento cliente = new ClienteEntrenamiento("localhost", 50051, fid);
					cliente.start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
				Usuario u = usuarioRepository.findById(id).orElse(null);
				
				

				
				//fichero.setEntrenamiento(entrenamientoRepository.save(entrenamiento));
				
				//u.modificarFichero(fichero);
				u.addPeticionesTrain();
				u.addPeticionSoporteFront();
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

}
