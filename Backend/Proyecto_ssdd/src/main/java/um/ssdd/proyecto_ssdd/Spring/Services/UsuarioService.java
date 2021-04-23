package um.ssdd.proyecto_ssdd.Spring.Services;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.ssdd.proyecto_ssdd.Spring.Entities.Fichero;
import um.ssdd.proyecto_ssdd.Spring.Entities.Usuario;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IFicheroRepository;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IUsuarioRepository;
import um.ssdd.proyecto_ssdd.Spring.Services.DTOs.UsuarioLogin;
import um.ssdd.proyecto_ssdd.Spring.Services.DTOs.UsuarioPost;
import um.ssdd.proyecto_ssdd.Spring.Services.DTOs.UsuarioPut;
import um.ssdd.proyecto_ssdd.Spring.Services.DTOs.UsuarioResponse;

@Service
public class UsuarioService {

	@Autowired
	public IUsuarioRepository usuarioRepository;
	
	@Autowired
	public IFicheroRepository ficheroRepository;
	
	@Autowired
	public ModelMapper modelMapper;

	public List<UsuarioResponse> getAll() {
		return usuarioRepository.findAll().stream().map(this::entityToResponse).collect(Collectors.toList());
	}

	public UsuarioResponse get(String id) {

		if (usuarioRepository.existsById(id)) {
			Usuario usuario = usuarioRepository.findById(id).orElse(null);
			return entityToResponse(usuario);
		}

		return null;

	}

	public UsuarioResponse post(UsuarioPost usuario) {

		if (usuario.getUser() == null || usuario.getPassword() == null || usuario.getNombre() == null
				|| usuario.getApellidos() == null)
			return null;

		if (usuarioRepository.existsByUser(usuario.getUser()))
			return null;


		Usuario u = postToEntity(usuario);
		u.addPeticionSoporteFront();
		return entityToResponse(usuarioRepository.save(u));

	}

	public int put(String id, UsuarioPut usuario) {

		if (!id.equals(usuario.getId()))
			return 0;

		if (usuario.getId() == null || usuario.getUser() == null || usuario.getPassword() == null
				|| usuario.getNombre() == null || usuario.getApellidos() == null)
			
			return 0;

		if (!usuarioRepository.existsById(id))
			return 1;

		
		Usuario u = putToEntity(usuario);
		
		u.addPeticionSoporteFront();
		usuarioRepository.save(u);

		return 2;
	}

	
	public int patch(String id, UsuarioPut usuario) {

		if (!id.equals(usuario.getId()))
			return 0;

		if (usuario.getId() == null || usuario.getUser() == null || usuario.getPassword() == null
				|| usuario.getNombre() == null || usuario.getApellidos() == null)
			
			return 0;

		Usuario user = usuarioRepository.findById(id).orElse(null);
		
		if (user == null)
			return 1;

		
		user.setId(id);
		user.setUser(usuario.getUser());
		user.setPassword(usuario.getPassword());
		user.setNombre(usuario.getNombre());
		
		user.setApellidos(usuario.getApellidos());
		user.addConexión();
		user.addVecesContrasenaModificada();
		
		
		
		usuarioRepository.save(user);

		return 2;
	}
	
	public boolean delete(String id) {

		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		if (usuario != null) {
			
			for (Fichero fichero: usuario.getFicheros()) {
				ficheroRepository.deleteById(fichero.getFID());
			}
			
			usuarioRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public UsuarioResponse login(UsuarioLogin usuario) {

		if (usuario.getUser() == null || usuario.getPassword() == null)
			return null;

		Usuario u = usuarioRepository.findByUserPassword(usuario.getUser(), usuario.getPassword());

		if (u != null) {
			u.addConexión();
			u.addPeticionSoporteFront();
			usuarioRepository.save(u);
			return entityToResponse(u);
		}
		return null;

		
	}
	

	private Usuario postToEntity(UsuarioPost usuario) {
		Usuario u = modelMapper.map(usuario, Usuario.class);
		u.initializeLists();
		return u;

	}

	private Usuario putToEntity(UsuarioPut usuario) {
		return modelMapper.map(usuario, Usuario.class);
	}

	private UsuarioResponse entityToResponse(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioResponse.class);
	}

}
