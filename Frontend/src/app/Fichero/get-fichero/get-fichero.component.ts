import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'app/Auth/authentication.service';
import { Usuario } from 'app/Entities/Usuario';
import { FicheroService } from '../fichero.service';
import { ListarFicherosComponent } from '../listar-ficheros/listar-ficheros.component';

@Component({
  selector: 'app-get-fichero',
  templateUrl: './get-fichero.component.html',
  styleUrls: ['./get-fichero.component.css']
})
export class GetFicheroComponent implements OnInit {

  text: string;
  usuario:Usuario;

  constructor(private ficheroService: FicheroService, private authService: AuthenticationService) { }

  ngOnInit(): void {

    this.ficheroService.get(this.ficheroService.getFid()).subscribe(data => {
      this.text = data

      this.usuario = this.authService.currentUserValue;
      this.usuario.peticionesSoporteFront++;

      this.authService.updateUser(this.usuario);
    })


  }

}
