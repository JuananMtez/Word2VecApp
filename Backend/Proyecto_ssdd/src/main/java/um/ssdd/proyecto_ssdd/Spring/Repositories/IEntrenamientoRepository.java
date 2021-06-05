package um.ssdd.proyecto_ssdd.Spring.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import um.ssdd.proyecto_ssdd.Entities.Entrenamiento;

public interface IEntrenamientoRepository extends MongoRepository<Entrenamiento, String> {

	@Query("{'WID' : ?0}")
	public List<Entrenamiento> findEntrenamientoById(String WID);

}
