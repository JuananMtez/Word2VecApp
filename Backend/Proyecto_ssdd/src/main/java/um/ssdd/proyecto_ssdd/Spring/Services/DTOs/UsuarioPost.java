package um.ssdd.proyecto_ssdd.Spring.Services.DTOs;

import java.io.Serializable;
import java.util.List;

import um.ssdd.proyecto_ssdd.Spring.Entities.Fichero;

public class UsuarioPost implements Serializable {


	private static final long serialVersionUID = 1L;
	private String user;
	private String password;
	private String nombre;
	private String apellidos;

	
	
	

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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
