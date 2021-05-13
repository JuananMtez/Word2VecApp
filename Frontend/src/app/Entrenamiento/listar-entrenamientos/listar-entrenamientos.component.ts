import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'app/Auth/authentication.service';
import { Entrenamiento } from 'app/Entities/Entrenamiento';
import { Usuario } from 'app/Entities/Usuario';
import { PalabrasDTO } from 'app/DTOs/PalabrasDTO';
import { EntrenamientoService } from '../entrenamiento.service';

@Component({
  selector: 'app-listar-entrenamientos',
  templateUrl: './listar-entrenamientos.component.html',
  styleUrls: ['./listar-entrenamientos.component.css']
})
export class ListarEntrenamientosComponent implements OnInit {

  entrenamientos: Entrenamiento[];
  usuario: Usuario;
  palabra: string;
  palabrasDTO: PalabrasDTO;



  constructor(private authService: AuthenticationService, private entrenamientoService: EntrenamientoService, private router: Router) {
    this.palabra = '';
  }

  ngOnInit(): void {

    if (this.authService.currentUserValue == null) {
      this.router.navigate(['/login']);
    }
    this.entrenamientoService.getAll()
    .subscribe(data => {
      this.entrenamientos = data;
    })

    this.usuario = this.authService.currentUserValue;
    this.usuario.peticionesSoporteFront++;

    this.authService.updateUser(this.usuario);

  }

  enviar(wid: string) {

    this.entrenamientoService.entrenado(wid, this.palabra)
    .subscribe(data => {
      this.palabrasDTO = data;
      this.usuario = this.authService.currentUserValue;
      this.usuario.peticionesWord2VecUse++;
      this.usuario.palabrasConsultadas++;
      this.authService.updateUser(this.usuario);

      alert(this.palabrasDTO.palabras);

    })

  }

}
