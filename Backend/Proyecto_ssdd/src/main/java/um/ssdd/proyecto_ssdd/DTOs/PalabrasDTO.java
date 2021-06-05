package um.ssdd.proyecto_ssdd.DTOs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PalabrasDTO {

	private String palabras;

	public PalabrasDTO() {
	}

	public PalabrasDTO(String palabras) {
		this.palabras = palabras;
	}

	public String getPalabras() {
		return palabras;
	}

	public void setPalabras(String palabras) {
		this.palabras = palabras;
	}

}
