package um.ssdd.proyecto_ssdd.Spring.Entities;

import org.springframework.data.annotation.Id;

public class Entrenamiento {
	
	@Id
	private String TID;
	
	
	
	public Entrenamiento() {}



	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
		TID = tID;
	}
	
	

}
