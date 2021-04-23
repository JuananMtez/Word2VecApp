import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/Auth/authentication.service';
import { UsuarioService } from 'src/app/Usuario/usuario.service';

@Component({
  selector: 'app-encabezado',
  templateUrl: './encabezado.component.html',
  styleUrls: ['./encabezado.component.css']
})
export class EncabezadoComponent implements OnInit {

  constructor(private usuarioService: UsuarioService, private authService: AuthenticationService, private router: Router) {
  }

  user: String;

  ngOnInit(): void {

  }

  logout(){

    if (this.authService.logout()) {
      alert("Deslogueado correctamente");
      this.router.navigate(["/login"]);
      this.user = null;
    }
  }

  eliminarUsuario() {

    this.usuarioService.delete(this.authService.currentUserValue.id)
    .subscribe(data => {

      alert("Usuario " + this.authService.currentUserValue.user + " eliminado correctamente")

      this.authService.logout();
      this.router.navigate(["/login"]);
    })





  }



  isUserLoged(): boolean{

    if (this.authService.currentUserValue != null) {
      this.user = this.authService.currentUserValue.user;
    }

    return this.authService.currentUserValue != null;
  }



}
