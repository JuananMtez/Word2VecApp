package um.ssdd.proyecto_ssdd.Spring.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import um.ssdd.proyecto_ssdd.Entities.Fichero;

public interface IFicheroRepository extends MongoRepository<Fichero, String> {

	@Query("{'usuarioIdPropietario' : ?0}")
	public List<Fichero> findByUserId(String usuarioId);

	@Query("{'usuarioIdPropietario' : ?0, 'FID': ?1}")
	public Fichero findByUserIdAndFid(String usuarioId, String FID);

	@Query("{'usuarioIdPropietario' : ?0, 'fileName': ?1}")
	public Fichero findByUserIdAndFileName(String usuarioId, String fileName);

	@Query("{'TID' : ?0}")
	public Fichero findByTid(String TID);

}
