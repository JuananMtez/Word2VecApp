package um.ssdd.proyecto_ssdd.DTOs;

public class EntrenamientoDTO {

	private String WID;
	private String nombreFichero;
	
	public EntrenamientoDTO() {
		
	}
	
	public EntrenamientoDTO( String wID, String nombreFichero ) {
		this.WID = wID;
		this.nombreFichero = nombreFichero;
	}

	public String getWID() {
		return WID;
	}

	public void setWID(String wID) {
		WID = wID;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	
	


	
	
	
}
