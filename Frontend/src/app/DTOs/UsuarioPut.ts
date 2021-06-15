export class UsuarioPut {

  id: string;
  user: string;
  password: string;
  nombre: string;
  apellidos: string;

  constructor(id: string, user:string, nombre: string, apellidos: string) {
    this.id = id;
    this.user = user;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.password = "";

  }
}

