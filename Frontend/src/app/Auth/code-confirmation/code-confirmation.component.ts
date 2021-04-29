import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CodigoDTO } from 'app/DTOs/CodigoDTO';
import { UsuarioService } from 'app/Usuario/usuario.service';


@Component({
  selector: 'app-code-confirmation',
  templateUrl: './code-confirmation.component.html',
  styleUrls: ['./code-confirmation.component.css']
})
export class CodeConfirmationComponent implements OnInit {


  codeConfirmacion: CodigoDTO;



  constructor(private usuarioService: UsuarioService, private router: Router) {

    this.codeConfirmacion = new CodigoDTO(usuarioService.getEmail());

  }

  ngOnInit(): void {
  }


  comprobarCodigo() {
    this.usuarioService.checkCode(this.codeConfirmacion).subscribe(
      data =>{
      alert("Código confirmado")
    },
    error =>{
        alert("El código de confirmación es erróneo")
        alert("El usuario se ha elimiando")
    })
    this.router.navigate(['/inicio']);

  }




}
