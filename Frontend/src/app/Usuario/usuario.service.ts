import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UsuarioPut } from '../DTOs/UsuarioPut';
import { UsuarioRegister } from '../DTOs/UsuarioRegister';
import { Usuario } from '../Entities/Usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {


  url='http://localhost:8080/api/usuarios';


  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<Usuario[]>(this.url);
  }

  get(id: number){
    return this.http.get<Usuario>(this.url + "/" + id);
  }

  register(registerDTO: UsuarioRegister) {
      return this.http.post<Usuario>(this.url, registerDTO);
  }

  put(usuario: UsuarioPut){
    return this.http.patch<Usuario>(this.url + "/" + usuario.id, usuario)
  }

  delete(id: String) {
      return this.http.delete<Usuario>(this.url + "/" + id);
  }



}
