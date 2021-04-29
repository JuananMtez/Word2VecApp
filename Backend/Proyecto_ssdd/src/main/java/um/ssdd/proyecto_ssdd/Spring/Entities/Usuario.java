package um.ssdd.proyecto_ssdd.Spring.Entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {



	@Id
	private String id;
	
	private String user;
	
	private String password;
	
	private String nombre;
	
	private String apellidos;
	
	private String token;
	
	private String correoElectronico;
	
	private List<Fichero> ficheros;
	
	
	
	
	
	private int ficherosSubidosTotalmente;
	private int ficherosAlmacenadosActualmente;

	
	private int peticionesSoporteFront;
	private int peticionesWord2VecTrain;
	private int peticionesWord2VecUse;
	
	
	private int vecesConectado;
	
	private int vecesContrasenaModificada;
	
	private int palabrasConsultadas; 
	private int entrenamientosHechos;
	
	private String codigoConfirmación;
	private boolean confirmado;
	
	

	public Usuario(String user, String password, String nombre, String apellidos, String token, String correoElectronico) {
		this.user = user;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		ficheros = new ArrayList<Fichero>();
		this.token = token;
		ficherosSubidosTotalmente = 0;
		ficherosAlmacenadosActualmente = 0;

		vecesConectado = 0;

		peticionesSoporteFront = 0;
		peticionesWord2VecTrain = 0;
		peticionesWord2VecUse = 0;
		
		vecesContrasenaModificada = 0;
		palabrasConsultadas = 0;
		entrenamientosHechos = 0;
		
		codigoConfirmación = null;
		
		this.correoElectronico = correoElectronico;
		
		confirmado = false;
		
		
		
	}
	
	public Usuario() {}

	public String getId() {
		return id;
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

	public int getFicherosAlmacenadosActualmente() {
		return ficherosAlmacenadosActualmente;
	}

	public void setFicherosAlmacenadosActualmente(int ficherosAlmacenadosActualmente) {
		this.ficherosAlmacenadosActualmente = ficherosAlmacenadosActualmente;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}



	public List<Fichero> getFicheros() {
		return ficheros;
	}

	public void setFicheros(List<Fichero> ficheros) {
		this.ficheros = ficheros;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public int getFicherosSubidosTotalmente() {
		return ficherosSubidosTotalmente;
	}

	public void setFicherosSubidosTotalmente(int ficherosSubidosTotalmente) {
		this.ficherosSubidosTotalmente = ficherosSubidosTotalmente;
	}



	public int getVecesConectado() {
		return vecesConectado;
	}

	public void setVecesConectado(int vecesConectado) {
		this.vecesConectado = vecesConectado;
	}

	public void addFichero(Fichero fichero) {
		ficheros.add(fichero);
	}
	
	public void initializeLists() {
		ficheros = new ArrayList<Fichero>();
	}
	
	public void deleteFichero(String fid) {
		
		Iterator<Fichero> iterator = ficheros.iterator();
		boolean removed = false;
		while (!removed && iterator.hasNext()) {
			Fichero fichero = iterator.next();
			
			if (fichero.getFID().equals(fid)) {
				iterator.remove();
				removed = true;
			}
		}
	}
	
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public void addConexión() {
		this.vecesConectado++;
	}
	
	public void addPeticionSoporteFront() {
		this.peticionesSoporteFront++;
	}
	
	
	public void addTotalFicherosSubidos() {
		this.ficherosSubidosTotalmente++;
	}
	
	public void addFicherosActuales() {
		this.ficherosAlmacenadosActualmente++;
	}
	
	public void removeFicherosActuales() {
		this.ficherosAlmacenadosActualmente--;
	}
	
	public void addVecesContrasenaModificada() {
		this.vecesContrasenaModificada++;
	}

	public String getCodigoConfirmación() {
		return codigoConfirmación;
	}

	public void setCodigoConfirmación(String codigoConfirmación) {
		this.codigoConfirmación = codigoConfirmación;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}
	
	
	
	
	
	

}
