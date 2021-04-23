package um.ssdd.proyecto_ssdd.Spring.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import um.ssdd.proyecto_ssdd.Spring.Entities.Fichero;
import um.ssdd.proyecto_ssdd.Spring.Entities.Usuario;

public interface IFicheroRepository extends MongoRepository<Fichero, String> {

	@Query("{'usuarioIdPropietario' : ?0}")
	public List<Fichero> findByUserId(String usuarioId);
	
	@Query("{'usuarioIdPropietario' : ?0, 'FID': ?1}")
	public Fichero findByUserIdAndFid(String usuarioId, String FID);
	
	@Query("{'usuarioIdPropietario' : ?0, 'fileName': ?1}")
	public Fichero findByUserIdAndFileName(String usuarioId, String fileName);

	

	
}
