import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Usuario } from '../Entities/Usuario';
import { map } from 'rxjs/operators';
import { LoginDTO } from '../DTOs/UsuarioLogin';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private currentUserSubject: BehaviorSubject<Usuario>;
  public currentUser: Observable<Usuario>;

  url = 'http://localhost:8080/api/usuarios/login';


  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<Usuario>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
   }

   public get currentUserValue(): Usuario{
    return this.currentUserSubject.value;
  }




  login(loginDto: LoginDTO){

    if (this.currentUserSubject.value == null) {

      return this.http.post<any>(this.url, loginDto)
      .pipe(map(user => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }))

    } else {
      
      alert("Para loguearse, primero debe desloguearse");
    }
  }

  logout():boolean {

    if (this.currentUserSubject.value != null){

      localStorage.removeItem('currentUser');
      this.currentUserSubject.next(null);
      return true;
    }
    return false;
  }

  updateUser(usuario: Usuario) {

    this.currentUserSubject.next(usuario);
    localStorage.setItem('currentUser', JSON.stringify(usuario));
  }


  getCurrentUser(){
    return this.currentUserSubject.value;
  }

}
