package um.ssdd.proyecto_ssdd.Spring.Services;

import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import um.ssdd.proyecto_ssdd.DTOs.CodigoDTO;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioLogin;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioPost;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioPut;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioResponse;
import um.ssdd.proyecto_ssdd.Entities.Fichero;
import um.ssdd.proyecto_ssdd.Entities.Usuario;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IFicheroRepository;
import um.ssdd.proyecto_ssdd.Spring.Repositories.IUsuarioRepository;

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
	
	public UsuarioResponse getEmail(String email) {

		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if (usuario != null)
			return entityToResponse(usuario);
		return null;

	}

	public UsuarioResponse post(UsuarioPost usuario) {

		if (usuario.getUser() == null || usuario.getPassword() == null || usuario.getNombre() == null
				|| usuario.getApellidos() == null || usuario.getCorreoElectronico() == null)
			return null;

		if (usuarioRepository.existsByUser(usuario.getUser()))
			return null;

		Usuario u = enviarCorreo(usuario);

		return entityToResponse(usuarioRepository.save(u));

	}

	private Usuario enviarCorreo(UsuarioPost usuario) {

		Usuario u = postToEntity(usuario);
		u.addPeticionSoporteFront();

		String host = "smtp.gmail.com";
		String from = "ppcpracticas2020@gmail.com";
		String password = "ppcpracticas";
		String codigoConfirmacion = null;

		Properties props = System.getProperties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "465");

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		};

		Session session = Session.getInstance(props, auth);
		MimeMessage message = new MimeMessage(session);

		try {

			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getCorreoElectronico()));
			message.setSubject("Codigo confirmación Word2Vec");

			SecureRandom random = new SecureRandom();
			int num = random.nextInt(100000);
			codigoConfirmacion = String.format("%05d", num);

			u.setCodigoConfirmación(codigoConfirmacion);

			message.setText("Codigo de confirmación: " + codigoConfirmacion);
			Transport.send(message);

		} catch (MessagingException me) {
			me.printStackTrace(); // Si se produce un error
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + me.getMessage());
		}

		return u;

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

			for (Fichero fichero : usuario.getFicheros()) {
				ficheroRepository.deleteById(fichero.getFID());
			}

			usuarioRepository.deleteById(id);
			return true;
		}
		return false;
	}

	// 0 bad request
	// 1 not found
	// 2 codigo no confirmado
	// 3 correcto
	public int login(UsuarioLogin usuario) {

		if (usuario.getUser() == null || usuario.getPassword() == null)
			return 0;

		Usuario u = usuarioRepository.findByUserPassword(usuario.getUser(), usuario.getPassword());

		if (u != null) {

			if (u.isConfirmado()) {

				u.addConexión();
				u.addPeticionSoporteFront();
				usuarioRepository.save(u);
				return 3;
				// return entityToResponse(u);

			} else {

				delete(u.getId());
				return 2;
			}

		}

		return 1;

	}

	public boolean checkCode(CodigoDTO codigoDTO) {

		Usuario usuario = usuarioRepository.findByEmail(codigoDTO.getCorreoElectronico());

		if (usuario != null) {

			if (usuario.getCodigoConfirmación().equals(codigoDTO.getCodigoConfirmacion())) {

				usuario.setConfirmado(true);
				usuario.addPeticionSoporteFront();
				usuarioRepository.save(usuario);
				return true;

			} else
				delete(usuario.getId());

		}

		return false;

	}

	public UsuarioResponse getUsuario(UsuarioLogin usuarioLogin) {

		Usuario usuario = usuarioRepository.findByUserPassword(usuarioLogin.getUser(), usuarioLogin.getPassword());
		return entityToResponse(usuario);
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
