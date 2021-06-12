package um.ssdd.proyecto_ssdd.TestClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;

import um.ssdd.proyecto_ssdd.DTOs.UsuarioPost;
import um.ssdd.proyecto_ssdd.DTOs.UsuarioResponse;

public class TestClient {

	public static void registroEliminacion() {

		System.out.print("Comprobando registro de usuario .... ");

		boolean ok = false;
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
				ok = true;
			} else {
				System.out.println("ERROR.");
				ok = false;
			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		if (ok) {
		
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
					ok = true;
				} else {
					System.out.println(" ERROR.");
					ok = false;
				}
				
				
				
				
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (ok) {
			
			System.out.print("Comprobando eliminación del usuario .... ");
			
			request = HttpRequest.newBuilder()
					.uri(URI.create("http://localhost:8080/api/usuarios/" + id))
					.DELETE()
					.build();
			
			try {
				HttpResponse<?> response = client.send(request, BodyHandlers.discarding());
				
				if (response.statusCode() == 204) {
					System.out.println(" OK.");
					ok = true;
				} else {
					System.out.println(" ERROR.");
					ok = false;
				}
				
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		}

		
		
		
		if (ok)
			System.out.println("\nTest realizado sin errores\n");
		else
			System.out.println("\nTest finalizado con errores\n");

	}

	public static void login() {

	}

	public static void subirFichero() {

	}

	public static void entrenarFichero() {

	}

	public static void ejecutarEntrenamiento() {

	}

	public static void main(String[] args) {

		System.out.println("TEST CLIENT WORD2VEC\n");
		System.out.println("Puedes probar las siguientes funcionalidades");
		System.out.println("1. Registro, obtención y eliminación de usuario");
		System.out.println("2. Login de usuario");
		System.out.println("3. Subir fichero");
		System.out.println("4. Entrenar fichero");
		System.out.println("5. Ejecutar entrenamiento");
		System.out.println("6. Comprobar todo");
		System.out.println("7. Explicaciones");
		System.out.println("8. Finalizar test de pruebas\n");

		boolean terminado = false;

		while (!terminado) {

			int numeroSeleccionado = 0;

			System.out.print("Por favor, seleccione un número -> ");

			boolean seleccionado = false;
			while (!seleccionado) {

				Scanner sc = new Scanner(System.in);
				String cadena = sc.nextLine();

				if (!cadena.equals("")) {
					numeroSeleccionado = Integer.valueOf(cadena);
				}

				if (numeroSeleccionado > 0 && numeroSeleccionado <= 8)
					seleccionado = true;
				else
					System.out.println("Por favor, seleccione un número válido ( 1..8 )");

			}
			System.out.println();
			switch (numeroSeleccionado) {
			case 1:
				registroEliminacion();
				break;

			case 2:
				login();
				break;
			case 3:
				subirFichero();
				break;

			case 4:
				entrenarFichero();
				break;

			case 5:
				ejecutarEntrenamiento();
				break;

			case 6:
				break;
				
			case 7:
				
				System.out.println("1. Registro, obtención y eliminación de usuario:");
				System.out.println("\tSe registra el usuario, posteriormente se intentará obtener de la base de datos y finalmente se borrará para que no haya problemas al realizar otra vez la prueba\n");

				
				break;

			case 8:
				terminado = true;
				System.out.println("Cerrando cliente de pruebas");

				break;

			}

		}
	}

}
