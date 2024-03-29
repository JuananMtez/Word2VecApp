package um.ssdd.proyecto_ssdd.Entities;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "entrenamientos")
public class Entrenamiento {

	@Id
	private String WID;

	private String nombreFichero;

	private Binary entrenamiento;

	public Entrenamiento(String nombreFichero, Binary entrenamiento) {
		this.nombreFichero = nombreFichero;
		this.entrenamiento = entrenamiento;
	}

	public Entrenamiento() {
	}

	public String getWID() {
		return WID;
	}

	public void setWID(String wID) {
		WID = wID;
	}

	public Binary getEntrenamiento() {
		return entrenamiento;
	}

	public void setEntrenamiento(Binary entrenamiento) {
		this.entrenamiento = entrenamiento;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

}
