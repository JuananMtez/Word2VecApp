package um.ssdd.proyecto_ssdd.DTOs;

import java.io.Serializable;

public class UsuarioLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String user;
	private String password;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
