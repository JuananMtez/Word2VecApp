import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from 'app/Auth/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class EntrenamientoService {

  url = 'http://localhost:8080/api/usuarios/';

  constructor(private http: HttpClient, private authService: AuthenticationService) {}

  entrenar(fid: string) {
    return this.http.get<String>(this.url + "/" + this.authService.currentUserValue.id + "/train/" + fid);
  }

}

