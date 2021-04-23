import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioRegister } from 'src/app/DTOs/UsuarioRegister';
import { Usuario } from 'src/app/Entities/Usuario';
import { UsuarioService } from 'src/app/Usuario/usuario.service';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  usuarioRegister: UsuarioRegister = {
    user: '',
    password: '',
    nombre: '',
    apellidos: ''
  };

  constructor(private authService: AuthenticationService, private usuarioService: UsuarioService, private router: Router) {
  }

  ngOnInit(): void {
    if(this.authService.currentUserValue != null) {
      this.router.navigate(["/inicio"]);
    }
  }


  registerUsuario() {

    this.usuarioService.register(this.usuarioRegister).subscribe(
      data =>{
      alert("Usuario registrado correctamente")
      this.router.navigate(['/login']);
    },
    error =>{
        alert("Nombre de usuario ya registrado")
    })
  }

}
