import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from 'app/Auth/authentication.service';
import { PalabrasDTO } from 'app/DTOs/PalabrasDTO';
import { Entrenamiento } from 'app/Entities/Entrenamiento';

@Injectable({
  providedIn: 'root'
})
export class EntrenamientoService {

  url = 'http://localhost:8080/api/usuarios/';

  urlWord2VecUse = "http://localhost:8081/Proyecto_ssdd/rest/w2buse"

  httpOptionsPlain = {
    headers: new HttpHeaders({
      'Accept': 'text/plain',
      'Content-Type': 'text/plain'
    }),
    'responseType': 'text'
  };

  constructor(private http: HttpClient, private authService: AuthenticationService) {}

  entrenar(fid: string) {
    return this.http.post<String>(this.url + this.authService.currentUserValue.id + "/train/" + fid, "")
  }

  getAll() {
    return this.http.get<Entrenamiento[]>(this.url + this.authService.currentUserValue.id + "/trains");
  }

  entrenado(wid: string, palabra: string) {
    return this.http.get<PalabrasDTO>(this.urlWord2VecUse + "/" + this.authService.currentUserValue.id + "/" + wid + "?word=" + palabra);
  }

}

