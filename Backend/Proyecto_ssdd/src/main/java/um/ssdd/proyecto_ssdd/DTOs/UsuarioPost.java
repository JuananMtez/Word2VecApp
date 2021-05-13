package um.ssdd.proyecto_ssdd.DTOs;

import java.io.Serializable;


public class UsuarioPost implements Serializable {


	private static final long serialVersionUID = 1L;
	private String user;
	private String password;
	private String nombre;
	private String apellidos;
	private String correoElectronico;

	
	
	

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
