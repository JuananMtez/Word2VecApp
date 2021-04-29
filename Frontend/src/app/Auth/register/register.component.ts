import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioRegister } from 'app/DTOs/UsuarioRegister';
import { UsuarioService } from 'app/Usuario/usuario.service';
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
    apellidos: '',
    correoElectronico: ''
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
      this.usuarioService.setEmail(this.usuarioRegister.correoElectronico)
      this.router.navigate(['/register/code']);
    },
    error =>{
        alert("Correo o nombre de usuario ya registrado")
    })
  }

}
