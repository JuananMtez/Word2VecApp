import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/Auth/authentication.service';
import { Usuario } from 'src/app/Entities/Usuario';

@Component({
  selector: 'app-estadisticas',
  templateUrl: './estadisticas.component.html',
  styleUrls: ['./estadisticas.component.css']
})
export class EstadisticasComponent implements OnInit {

  usuario: Usuario;
  constructor(private authService: AuthenticationService) {

  }

  ngOnInit(): void {
    this.usuario = this.authService.getCurrentUser();
  }

}
