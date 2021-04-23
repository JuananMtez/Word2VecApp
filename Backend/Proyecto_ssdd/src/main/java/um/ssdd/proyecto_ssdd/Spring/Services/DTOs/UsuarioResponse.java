package um.ssdd.proyecto_ssdd.Spring.Services.DTOs;

import java.io.Serializable;
import java.util.List;

public class UsuarioResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String user;
	private String nombre;
	private String apellidos;
	private List<FicheroDTO> ficheros;
	
	private int ficherosAlmacenadosActualmente;
	private int ficherosSubidosTotalmente;
	private int vecesConectado;
	
	private int peticionesSoporteFront;
	private int peticionesWord2VecTrain;
	private int peticionesWord2VecUse;
	
	private int vecesContrasenaModificada;
	private int palabrasConsultadas; 
	private int entrenamientosHechos;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}
	

	public int getVecesContrasenaModificada() {
		return vecesContrasenaModificada;
	}

	public void setVecesContrasenaModificada(int vecesContrasenaModificada) {
		this.vecesContrasenaModificada = vecesContrasenaModificada;
	}

	public int getPalabrasConsultadas() {
		return palabrasConsultadas;
	}

	public void setPalabrasConsultadas(int palabrasConsultadas) {
		this.palabrasConsultadas = palabrasConsultadas;
	}

	public int getEntrenamientosHechos() {
		return entrenamientosHechos;
	}

	public void setEntrenamientosHechos(int entrenamientosHechos) {
		this.entrenamientosHechos = entrenamientosHechos;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	
	public int getFicherosAlmacenadosActualmente() {
		return ficherosAlmacenadosActualmente;
	}

	public void setFicherosAlmacenadosActualmente(int ficherosAlmacenadosActualmente) {
		this.ficherosAlmacenadosActualmente = ficherosAlmacenadosActualmente;
	}

	public List<FicheroDTO> getFicheros() {
		return ficheros;
	}

	public void setFicheros(List<FicheroDTO> ficheros) {
		this.ficheros = ficheros;
	}

	public int getFicherosSubidosTotalmente() {
		return ficherosSubidosTotalmente;
	}

	public void setFicherosSubidosTotalmente(int ficherosSubidosTotalmente) {
		this.ficherosSubidosTotalmente = ficherosSubidosTotalmente;
	}



	public int getPeticionesSoporteFront() {
		return peticionesSoporteFront;
	}

	public void setPeticionesSoporteFront(int peticionesSoporteFront) {
		this.peticionesSoporteFront = peticionesSoporteFront;
	}

	public int getPeticionesWord2VecTrain() {
		return peticionesWord2VecTrain;
	}

	public void setPeticionesWord2VecTrain(int peticionesWord2VecTrain) {
		this.peticionesWord2VecTrain = peticionesWord2VecTrain;
	}

	public int getPeticionesWord2VecUse() {
		return peticionesWord2VecUse;
	}

	public void setPeticionesWord2VecUse(int peticionesWord2VecUse) {
		this.peticionesWord2VecUse = peticionesWord2VecUse;
	}

	public int getVecesConectado() {
		return vecesConectado;
	}

	public void setVecesConectado(int vecesConectado) {
		this.vecesConectado = vecesConectado;
	}
	

}
