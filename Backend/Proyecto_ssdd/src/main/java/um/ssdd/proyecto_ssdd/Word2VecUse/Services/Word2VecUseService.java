package um.ssdd.proyecto_ssdd.Word2VecUse.Services;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.bson.types.Binary;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

import com.google.common.io.Files;

import um.ssdd.proyecto_ssdd.DTOs.PalabrasDTO;
import um.ssdd.proyecto_ssdd.Word2VecUse.DAO.Word2VecUseDAO;

public class Word2VecUseService {

		
	public PalabrasDTO obtenerPalabras(String id, String wid, String word) {
		
		
		Word2VecUseDAO word2VecUseDAO = new Word2VecUseDAO();
		
		Binary binary = word2VecUseDAO.findEntrenamiento(id, wid);
				
        File tempFile;

	
		
		
	    try {
	    	
	    	tempFile = File.createTempFile("pathToWriteto", ".bin");
    		tempFile.deleteOnExit();
    	
    	 
			Files.write(binary.getData(), tempFile);			
			Word2Vec vec = WordVectorSerializer.readWord2VecModel(tempFile);

	    	Collection<String> lst = vec.wordsNearestSum(word, 10);
	    	
	        return new PalabrasDTO(lst.toString()); 
	
	    } catch (NullPointerException | IOException e) {}
		
		
		return new PalabrasDTO("");
	}
	
}
