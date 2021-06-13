package um.ssdd.proyecto_ssdd.TestClient;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;

import um.ssdd.proyecto_ssdd.DTOs.FicheroDTO;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioLogin;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioPost;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioResponse;

public class TestClient {

	public static boolean registroEliminacion() {

		System.out.print("Comprobando registro de usuario .... ");

		String email = "prueba@prueba.com";
		String id = null;

		UsuarioPost usuario = new UsuarioPost();
		usuario.setUser("RegistroPrueba");
		usuario.setPassword("1234");
		usuario.setNombre("Prueba");
		usuario.setApellidos("Test Client");
		usuario.setCorreoElectronico(email);

		Gson gson = new Gson();
		String mensaje = gson.toJson(usuario);

		HttpClient client = HttpClient.newBuilder().build();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/api/usuarios"))
				.header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString(mensaje))
				.build();

		try {
			HttpResponse<?> response = client.send(request, BodyHandlers.discarding());

			if (response.statusCode() == 200) {
				System.out.println("OK.");
			} else {
				System.out.println("ERROR.");
				return false;
			}

		} catch (IOException | InterruptedException e ) {
			System.out.println("ERROR.");
			return false;
		}
		
		
		System.out.print("Comprobando obtención del usuario de la base de datos .... ");
		
		request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/api/usuarios/email/" + email))
				.build();
		
		
		
		try {
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			UsuarioResponse user = gson.fromJson(response.body(), UsuarioResponse.class);
			
			if (user != null) {
				id = user.getId();
				System.out.println(" OK.");
			} else {
				System.out.println(" ERROR.");
				return false;
			}
			
			
			
			
		} catch (IOException | InterruptedException e) {
			System.out.println("ERROR.");
			return false;
		}
	
	

		
		System.out.print("Comprobando eliminación del usuario .... ");
		
		request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/api/usuarios/" + id))
				.DELETE()
				.build();
		
		try {
			HttpResponse<?> response = client.send(request, BodyHandlers.discarding());
			
			if (response.statusCode() == 204) {
				System.out.println(" OK.");
			} else {
				System.out.println(" ERROR.");
				return false;
			}
			
		} catch (IOException | InterruptedException e) {
			System.out.println("ERROR.");
			return false;
		}
		
		return true;
	}

	public static boolean login() {
		
		System.out.print("Comprobando login de usuario .... ");

		
		UsuarioLogin usuario = new UsuarioLogin();
		usuario.setUser("PruebaLogin");
		usuario.setPassword("1234");
		
		Gson gson = new Gson();
		String mensaje = gson.toJson(usuario);

		HttpClient client = HttpClient.newBuilder().build();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/api/usuarios/login"))
				.header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString(mensaje))
				.build();
		try {
			HttpResponse<?> response = client.send(request, BodyHandlers.discarding());

			if (response.statusCode() == 200) {
				System.out.println("OK.");
			} else {
				System.out.println("ERROR.");
				return false;
			}

		} catch (IOException | InterruptedException e) {
			System.out.println("ERROR.");
			return false;
		}
		
		return true;
	}

	public static boolean funcionalidadesFichero() {
		
		

		String email = "juanantonio.martinezl@um.es";
		
		UsuarioResponse user = null;
		FicheroDTO fichero = null;
		String FID = null;
		
		
		System.out.print("Comprobando obtención del usuario de la base de datos .... ");
		
		HttpClient client = HttpClient.newBuilder().build();

		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/api/usuarios/email/" + email))
				.build();
				
		try {
			
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			Gson gson = new Gson();
			user = gson.fromJson(response.body(), UsuarioResponse.class);
			
			if (user != null) {
				System.out.println(" OK.");
			} else {
				System.out.println(" ERROR.");
				return false;
			}

		} catch (IOException | InterruptedException e) {
			System.out.println("ERROR.");
			return false;
		}
		
			
		System.out.print("Subiendo fichero .... ");

		CloseableHttpClient cliente = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/api/usuarios/" + user.getId() + "/file");
		
		
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addBinaryBody(
		  "file", new File("raw_sentences.txt"), ContentType.APPLICATION_OCTET_STREAM, "raw_sentences.txt");
		
		HttpEntity multipart = builder.build();
		httpPost.setEntity(multipart);
		
		try {
			CloseableHttpResponse response = cliente.execute(httpPost);
		    cliente.close();
		    
		    FID = response.getHeaders("Location")[0].getValue();
		   
		
		    if (FID != null) {
				System.out.println(" OK.");

		    } else {
				System.out.println(" ERROR.");
				return false;
		    }
		    
		    
		} catch (IOException e) {
			System.out.println(" ERROR.");
			return false;
		}
		
		
		
		System.out.print("Realizando entrenamiento .... ");

		
		cliente = HttpClients.createDefault();
		httpPost = new HttpPost("http://localhost:8080/api/usuarios/" + user.getId() + "/train/" + FID);
		
	
		
		try {
			CloseableHttpResponse response = cliente.execute(httpPost);
		    cliente.close();
		    		   
		    boolean terminado = false;
		    int intentos = 3;
		    
		    while (!terminado && intentos > 0) {
		    	
			    request = HttpRequest.newBuilder()
						.uri(URI.create("http://localhost:8080/api/usuarios/" + user.getId() + "/file/" + FID))
						.build();
			    
			    HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());
				
				Gson gson = new Gson();
				fichero = gson.fromJson(respuesta.body(), FicheroDTO.class);
				
				
				
				if (fichero.getEntrenamientoWID() != null) {
					terminado = true;

				} else {
					Thread.sleep(4000);
					intentos--;
					
				}
			    
		    }
		    
		    
		    if (terminado) {
				System.out.println(" OK.");

		    } else {
		    	
				System.out.println(" ERROR.");
				eliminarFichero(user.getId(), FID);
		    	return false;
		    }
		    
 
		    
		} catch (IOException | InterruptedException e) {
			System.out.println(" ERROR.");
			return false;
		}
		
		System.out.print("Ejecutando entrenamiento .... ");

	    
		client = HttpClient.newBuilder().build();

		
		request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8081/Proyecto_ssdd/rest/w2buse/" + user.getId() + "/" + fichero.getEntrenamientoWID() + "?word=day"))
				.build();
		
		 try {
			HttpResponse<String> respuesta = client.send(request, BodyHandlers.ofString());
			
			if (respuesta.statusCode() == 200) {
				System.out.println(" OK.");
			} else {
				System.out.println(" ERROR.");
				eliminarFichero(user.getId(), FID);
				return false;
				
			}
			
			
		} catch (IOException | InterruptedException e) {
			System.out.println(" ERROR.");
			eliminarFichero(user.getId(), FID);
			return false;
		}
		
	    
		System.out.print("Eliminar fichero .... ");
		 
		 
		if(eliminarFichero(user.getId(), FID)) {
			System.out.println(" OK.");
		} else {
			System.out.println(" ERROR.");
			return false;
		}
		
		return true;
		
	}

	private static boolean eliminarFichero(String id, String FID) {
		
		HttpClient client = HttpClient.newBuilder().build();

		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/api/usuarios/" + id + "/file/" + FID))
				.DELETE()
				.build();
		
		try {
				
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			if (response.statusCode() == 204) {
				return true;
			}
			return false;


		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		
		return false;
		
	}

	public static void main(String[] args) {

		System.out.println("TEST CLIENT WORD2VEC\n");
		System.out.println("Puedes probar las siguientes funcionalidades");
		System.out.println("1. Registro, obtención y eliminación de usuario");
		System.out.println("2. Login de usuario");
		System.out.println("3. Subir fichero, entrenar fichero, ejecutar entrenamiento y eliminar fichero");
		System.out.println("4. Explicaciones");
		System.out.println("5. Finalizar test de pruebas\n");

		boolean terminado = false;
		Scanner sc = null;

		while (!terminado) {

			int numeroSeleccionado = 0;

			System.out.print("Por favor, seleccione un número -> ");

			boolean seleccionado = false;
			while (!seleccionado) {

				sc = new Scanner(System.in);
				String cadena = sc.nextLine();

				try {
					numeroSeleccionado = Integer.parseInt(cadena);
				} catch (NumberFormatException e) {}
				//if (!cadena.equals("")) {
					//numeroSeleccionado = Integer.valueOf(cadena);
				//}

				if (numeroSeleccionado > 0 && numeroSeleccionado <= 5)
					seleccionado = true;
				else
					System.out.print("Por favor, seleccione un número válido ( 1..5 ) -> ");

			}
			System.out.println();
			switch (numeroSeleccionado) {
			case 1:
				if(registroEliminacion())
					System.out.println("\nTest realizado sin errores\n");
				else
					System.out.println("\nTest finalizado con errores\n");
				
				break;
			case 2:
				if(login())
					System.out.println("\nTest realizado sin errores\n");
				else
					System.out.println("\nTest finalizado con errores\n");
					
				break;
			case 3:
				if(funcionalidadesFichero())
					System.out.println("\nTest realizado sin errores\n");
				else
					System.out.println("\nTest finalizado con errores\n");				
				break;

			case 4:
				
				System.out.println("1. Registro, obtención y eliminación de usuario:");
				System.out.println("\tSe registra el usuario.\n\tPosteriormente se intentará recuperar de la base de datos para obtener su id.\n\tFinalmente se borrará para que no haya problemas al realizar otra vez la prueba.\n");

				System.out.println("2. Login de usuario:");
				System.out.println("\tSe intentará loguear con una cuenta que ha sido creada previamente para poder realizar la prueba.\n");
				
				System.out.println("3. Subir fichero, entrenar fichero, ejecutar entrenamiento y eliminar fichero:");
				System.out.println("\tSe almacenará un fichero en la cuenta de un usuario previamente creado.\n\tSe entrenará.\n\tSe realizará un entrenamiento con una palabra aleatoria.\n\tFinalmente se borra el fichero para que se pueda realizar la prueba tantas veces como uno quiera.\n");
				break;

			case 5:
				terminado = true;
				sc.close();
				System.out.println("Cerrando cliente de pruebas");

				break;

			}
		}
	}

}
