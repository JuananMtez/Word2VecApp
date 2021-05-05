package um.ssdd.proyecto_ssdd.Spring.Entities;


import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ficheros")
public class Fichero {
	
	@Id
	private String FID;
	private String fileName;
	
	private String usuarioIdPropietario;
	
	private String TID;
	private Entrenamiento entrenamiento;
	
	private int vecesLeido;
	
	private Binary fichero;
	
	
	public Fichero(String usuarioIdPropietario, String fileName) {
		this.usuarioIdPropietario = usuarioIdPropietario;
		this.fileName = fileName;
		this.vecesLeido = 0;
		entrenamiento = null;
		TID = "";
	}
	
	public Fichero() {}

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public Binary getFichero() {
		return fichero;
	}

	public void setFichero(Binary fichero) {
		this.fichero = fichero;
	}


	public String getUsuarioIdPropietario() {
		return usuarioIdPropietario;
	}

	public void setUsuarioIdPropietario(String usuarioIdPropietario) {
		this.usuarioIdPropietario = usuarioIdPropietario;
	}
	
	

	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
		TID = tID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getVecesLeido() {
		return vecesLeido;
	}

	public void setVecesLeido(int vecesLeido) {
		this.vecesLeido = vecesLeido;
	}
	
	public void sumarLectura() {
		this.vecesLeido++;
	}

	public Entrenamiento getEntrenamiento() {
		return entrenamiento;
	}

	public void setEntrenamiento(Entrenamiento entrenamiento) {
		this.entrenamiento = entrenamiento;
	}	
}