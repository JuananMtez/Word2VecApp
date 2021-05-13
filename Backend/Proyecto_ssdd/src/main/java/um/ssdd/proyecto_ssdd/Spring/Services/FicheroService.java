package um.ssdd.proyecto_ssdd.Spring.Services;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import um.ssdd.proyecto_ssdd.DTOs.FicheroDTO;

import um.ssdd.proyecto_ssdd.Entities.Fichero;
import um.ssdd.proyecto_ssdd.Entities.Usuario;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IEntrenamientoRepository;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IFicheroRepository;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IUsuarioRepository;


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

			Fichero fichero = ficheroRepository.findByUserIdAndFid(id, fid);
			
			if (fichero.getEntrenamiento() != null)
				entrenamientoRepository.deleteById(fichero.getEntrenamiento().getWID());
			
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
	
	
	


	
	public boolean fileNameRepeated(String id, MultipartFile file) {
		return ficheroRepository.findByUserIdAndFileName(id, file.getOriginalFilename()) != null;
	}

	private FicheroDTO entityToResponse(Fichero fichero) {		
		return modelMapper.map(fichero, FicheroDTO.class);
	}
	
	
	

	
}
