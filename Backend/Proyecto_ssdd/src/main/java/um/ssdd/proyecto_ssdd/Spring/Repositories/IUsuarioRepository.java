package um.ssdd.proyecto_ssdd.Spring.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import um.ssdd.proyecto_ssdd.Entities.Usuario;

@Repository
@Transactional
public interface IUsuarioRepository extends MongoRepository<Usuario, String> {

	@Query("{'user' : ?0 , 'password' : ?1}")
	public Usuario findByUserPassword(String user, String password);

	@Query("{'correoElectronico' : ?0}")
	public Usuario findByEmail(String correoElectronico);
	
	@Query("{'user' : ?0}")
	public Usuario findByUser(String user);

	@Query(exists = true)
	public Boolean existsByUser(String user);

}
