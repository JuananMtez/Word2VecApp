package um.ssdd.proyecto_ssdd.Spring.Controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import um.ssdd.proyecto_ssdd.DTOs.CodigoDTO;
import um.ssdd.proyecto_ssdd.DTOs.EntrenamientoDTO;
import um.ssdd.proyecto_ssdd.DTOs.FicheroDTO;
import um.ssdd.proyecto_ssdd.DTOs.PalabrasDTO;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioLogin;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioPost;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioPut;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioResponse;
import um.ssdd.proyecto_ssdd.Spring.Services.EntrenamientoService;
import um.ssdd.proyecto_ssdd.Spring.Services.FicheroService;
import um.ssdd.proyecto_ssdd.Spring.Services.UsuarioService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/usuarios")
public class Controller {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FicheroService ficheroService;

	@Autowired
	private EntrenamientoService entrenamientoService;

	@GetMapping
	public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {

		return ResponseEntity.ok(usuarioService.getAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponse> getUsuario(@PathVariable("id") String id) {

		UsuarioResponse u = usuarioService.get(id);

		if (u != null)
			return ResponseEntity.ok(u);

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<UsuarioResponse> postUsuario(@RequestBody UsuarioPost usuario) {

		UsuarioResponse user = usuarioService.post(usuario);

		if (user == null)
			return ResponseEntity.status(409).build();

		return ResponseEntity.ok(user);

	}

	@PutMapping("/{id}")
	public ResponseEntity<String> putUsuario(@PathVariable("id") String id, @RequestBody UsuarioPut usuario) {

		switch (usuarioService.put(id, usuario)) {
		case 0:
			return ResponseEntity.badRequest().build();
		case 1:
			return ResponseEntity.notFound().build();
		case 2:
			return ResponseEntity.noContent().build();
		}
		return null;
	}

	@PatchMapping("/{id}")
	public ResponseEntity<String> patchUsuario(@PathVariable("id") String id, @RequestBody UsuarioPut usuario) {

		switch (usuarioService.patch(id, usuario)) {
		case 0:
			return ResponseEntity.badRequest().build();
		case 1:
			return ResponseEntity.notFound().build();
		case 2:
			return ResponseEntity.noContent().build();
		}
		return null;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUsuario(@PathVariable("id") String id) {

		if (usuarioService.delete(id))
			return ResponseEntity.noContent().build();

		return ResponseEntity.notFound().build();
	}

	@PostMapping("code")
	public ResponseEntity<String> checkConfirmationCode(@RequestBody CodigoDTO codigoDTO) {

		if (usuarioService.checkCode(codigoDTO))
			return ResponseEntity.ok().build();

		return ResponseEntity.notFound().build();

	}

	@PostMapping("/login")
	public ResponseEntity<UsuarioResponse> loginUsuario(@RequestBody UsuarioLogin usuario) {

		switch (usuarioService.login(usuario)) {

		case 0:
			return ResponseEntity.badRequest().build();

		case 1:
			return ResponseEntity.notFound().build();

		case 2:
			return ResponseEntity.status(401).build();

		case 3:
			return ResponseEntity.ok(usuarioService.getUsuario(usuario));

		}

		return null;

	}

	@PostMapping("/{id}/file")
	public ResponseEntity<String> uploadFile(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {

		URI uri = ficheroService.post(id, file);

		if (uri != null)
			return ResponseEntity.created(uri).build();

		return ResponseEntity.badRequest().build();

	}

	@GetMapping("/files")
	public ResponseEntity<List<FicheroDTO>> getAllFicheros() {
		return ResponseEntity.ok(ficheroService.getAll());

	}

	@GetMapping("/{id}/files")
	public ResponseEntity<List<FicheroDTO>> getAllFicherosUsuario(@PathVariable("id") String id) {
		return ResponseEntity.ok(ficheroService.getAllUsuario(id));
	}

	@GetMapping("/{id}/file/{fid}")
	public ResponseEntity<FicheroDTO> getFichero(@PathVariable("id") String id, @PathVariable("fid") String fid) {
		FicheroDTO fichero = ficheroService.get(id, fid);
		if (fichero != null)
			return ResponseEntity.ok(fichero);
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/file/{fid}/text")
	public ResponseEntity<String> getFicheroText(@PathVariable("id") String id, @PathVariable("fid") String fid) {
		String text = ficheroService.getText(id, fid);
		if (text != null)
			return ResponseEntity.ok(text);
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}/file/{fid}")
	public ResponseEntity<String> deleteFichero(@PathVariable("id") String id, @PathVariable("fid") String fid) {

		if (ficheroService.delete(id, fid)) {

			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();

	}

	@GetMapping("/{id}/trains")
	public ResponseEntity<List<EntrenamientoDTO>> getAllEntrenamientosUsuario(@PathVariable("id") String id) {
		return ResponseEntity.ok(entrenamientoService.getAll(id));
	}

	@PostMapping("/{id}/train/{fid}")
	public ResponseEntity<String> entrenarFichero(@PathVariable("id") String id, @PathVariable("fid") String fid) {

		URI uri = entrenamientoService.entrenar(id, fid);

		if (uri != null)
			return ResponseEntity.created(uri).build();

		return ResponseEntity.badRequest().build();

	}

	@GetMapping("/{id}/train/{wid}")
	public ResponseEntity<PalabrasDTO> entrenado(@PathVariable("id") String id, @PathVariable("wid") String wid,
			@RequestParam String word) {

		PalabrasDTO a = entrenamientoService.entrenado(wid, word);

		if (a != null)
			return ResponseEntity.ok(a);
		return ResponseEntity.notFound().build();

	}

}
