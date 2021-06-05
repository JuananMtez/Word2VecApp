package um.ssdd.proyecto_ssdd.Word2VecUse.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import um.ssdd.proyecto_ssdd.Word2VecUse.Services.Word2VecUseService;

@Path("/w2buse")
public class Word2VecUseController {

	@GET()
	@Path("{id}/{wid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerPalabras(@PathParam("id") String id, @PathParam("wid") String wid,
			@QueryParam("word") String word) {

		Word2VecUseService word2VecUseService = new Word2VecUseService();

		return Response.ok(word2VecUseService.obtenerPalabras(id, wid, word)).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600").build();
	}

}
