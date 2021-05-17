import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthenticationService } from '../Auth/authentication.service';
import { Fichero } from '../Entities/Fichero';

@Injectable({
  providedIn: 'root'
})
export class FicheroService {

  url='http://localhost:8080/api/usuarios';

  fid: string;

  constructor(private http: HttpClient, private authService: AuthenticationService) { }


  getFid(){
    return this.fid;
  }
  setFid(fid: string) {
    this.fid = fid;
  }

  getAll() {
    return this.http.get<Fichero[]>(this.url + "/" + this.authService.currentUserValue.id + "/files");
  }

  get(fid: string) {
    return this.http.get<Fichero>(this.url + "/" + this.authService.currentUserValue.id + "/file/" + fid);
  }

  getText(fid: string) {
    const httpOptionsPlain = {
      headers: new HttpHeaders({
        'Accept': 'text/plain',
        'Content-Type': 'text/plain'
      }),
      'responseType': 'text'
    };
    return this.http.get<string>(this.url + "/" + this.authService.currentUserValue.id + "/file/" + fid + "/text", {responseType: 'text' as 'json'});

  }

  delete(fid: string) {
    return this.http.delete<Fichero>(this.url + "/" + this.authService.currentUserValue.id + "/file/" + fid);
  }

  post(fichero: File) {

    const formData: FormData = new FormData();
    formData.append('file', fichero, fichero.name);
    let headers = new Headers();
    headers.set('Accept', 'application/json');

    return this.http.post(this.url + "/" + this.authService.currentUserValue.id + "/file", formData).pipe(
      map(response => {
        return true;
      } ));



  }

}
