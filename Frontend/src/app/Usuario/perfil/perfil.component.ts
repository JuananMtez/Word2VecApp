import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/Auth/authentication.service';
import { UsuarioService } from '../usuario.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  constructor(private usuarioService: UsuarioService, private authService: AuthenticationService, private router: Router) { }

  ngOnInit(): void {
  }

  moveToEditar(){
    this.router.navigate(["/usuario/modificar"]);

  }

  moveToEstadisticas() {
    this.router.navigate(["/usuario/estadisticas"]);
  }

  eliminarUsuario() {

    if (confirm("Seguro que quieres eliminar al Usuario: " + this.authService.currentUserValue.user)) {
      this.usuarioService.delete(this.authService.currentUserValue.id)
      .subscribe(data => {

        alert("Usuario " + this.authService.currentUserValue.user + " eliminado correctamente")

        this.authService.logout();
        this.router.navigate(["/login"]);
      })
    }
  }
}
