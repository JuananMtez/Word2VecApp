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

  usuarioError: boolean;
  passwordError: boolean;
  nombreError: boolean;
  apellidosError: boolean;
  correoElectronicoError: boolean;


  constructor(private authService: AuthenticationService, private usuarioService: UsuarioService, private router: Router) {
    this.usuarioError = false;
    this.passwordError = false;
    this.nombreError = false;
    this.apellidosError = false;
    this.correoElectronicoError = false;
  }

  ngOnInit(): void {
    if(this.authService.currentUserValue != null) {
      this.router.navigate(["/inicio"]);
    }
  }


  registerUsuario() {




    if (this.usuarioRegister.user != '' && this.usuarioRegister.password != ''
      && this.usuarioRegister.nombre != '' && this.usuarioRegister.apellidos != ''
      && this.usuarioRegister.correoElectronico != '') {

      this.usuarioService.register(this.usuarioRegister).subscribe(
        data =>{
        this.usuarioService.setEmail(this.usuarioRegister.correoElectronico)
        this.router.navigate(['/register/code']);
      },
      error =>{
          alert("Correo o nombre de usuario ya registrado")
      })
    } else {

      if (this.usuarioRegister.user == '') {
        this.usuarioError = true
      }

      if (this.usuarioRegister.password == '') {
        this.passwordError = true;
      }

      if (this.usuarioRegister.nombre == '') {
        this.nombreError = true;
      }

      if (this.usuarioRegister.apellidos == '') {
        this.apellidosError = true;
      }

      if (this.usuarioRegister.correoElectronico == '') {
        this.correoElectronicoError = true;

      }


    }
  }

}
