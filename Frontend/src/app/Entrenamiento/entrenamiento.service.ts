import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from 'app/Auth/authentication.service';
import { PalabrasDTO } from 'app/DTOs/PalabrasDTO';
import { Entrenamiento } from 'app/Entities/Entrenamiento';

@Injectable({
  providedIn: 'root'
})
export class EntrenamientoService {

  url = 'http://localhost:8080/api/usuarios/';

  constructor(private http: HttpClient, private authService: AuthenticationService) {}

  entrenar(fid: string) {
    return this.http.post<String>(this.url + "/" + this.authService.currentUserValue.id + "/train/" + fid, "")
  }

  getAll() {
    return this.http.get<Entrenamiento[]>(this.url + this.authService.currentUserValue.id + "/trains");
  }

  entrenado(wid: string, palabra: string) {
    return this.http.get<PalabrasDTO>(this.url + this.authService.currentUserValue.id + "/train/" + wid + "?word=" + palabra);
  }

}

