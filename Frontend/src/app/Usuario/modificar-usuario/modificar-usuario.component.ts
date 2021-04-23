import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/Auth/authentication.service';
import { UsuarioPut } from 'src/app/DTOs/UsuarioPut';
import { Usuario } from 'src/app/Entities/Usuario';
import { UsuarioService } from '../usuario.service';

@Component({
  selector: 'app-modificar-usuario',
  templateUrl: './modificar-usuario.component.html',
  styleUrls: ['./modificar-usuario.component.css']
})
export class ModificarUsuarioComponent implements OnInit {

  usuario: Usuario;
  usuarioPut: UsuarioPut;

  constructor(private authService: AuthenticationService, private usuarioService: UsuarioService, private router: Router) {
    this.usuarioPut = new UsuarioPut(this.authService.currentUserValue.id as string, this.authService.currentUserValue.user  as string, this.authService.currentUserValue.nombre as string, this.authService.currentUserValue.apellidos as string);


  }

  ngOnInit(): void {


  }


  modificar() {

    if (this.usuarioPut.password == "") {
      alert("Inserte una contraseña")
    } else {
      this.usuarioService.put(this.usuarioPut)
      .subscribe(() => {

        this.usuario = this.authService.currentUserValue;
        this.usuario.password = this.usuarioPut.password;
        this.usuario.peticionesSoporteFront++;
        this.usuario.vecesContrasenaModificada++;
        this.authService.updateUser(this.usuario);

        alert("Usuario modificado correctamente")
        this.router.navigate(['/inicio']);
      });
    }
  }
}
