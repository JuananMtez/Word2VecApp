package um.ssdd.proyecto_ssdd.Spring.Services.DTOs;

import java.io.Serializable;
import java.util.List;

public class FicheroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String FID;
	private String usuarioId;
	private String fileName;
	private String vecesLeido;
	
	private String TID;

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getVecesLeido() {
		return vecesLeido;
	}

	public void setVecesLeido(String vecesLeido) {
		this.vecesLeido = vecesLeido;
	}

	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
		TID = tID;
	}






	
}
