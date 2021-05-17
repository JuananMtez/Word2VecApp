import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Fichero } from 'app/Entities/Fichero';
import { AuthenticationService } from 'app/Auth/authentication.service';
import { Usuario } from 'app/Entities/Usuario';
import { FicheroService } from '../fichero.service';
import { EntrenamientoService } from 'app/Entrenamiento/entrenamiento.service';
import { GetFicheroComponent } from '../get-fichero/get-fichero.component';

@Component({
  selector: 'app-listar-ficheros',
  templateUrl: './listar-ficheros.component.html',
  styleUrls: ['./listar-ficheros.component.css']
})
export class ListarFicherosComponent implements OnInit {

  ficheros: Fichero[];
  fichero: File;
  @ViewChild('fileInput') fileInput
  usuario: Usuario;


  constructor(private authService: AuthenticationService, private ficheroService: FicheroService, private entrenamientoService: EntrenamientoService, private router: Router) { }

  ngOnInit(): void {

    if (this.authService.currentUserValue == null) {
      this.router.navigate(['/login']);
    }
    this.ficheroService.getAll()
    .subscribe(data => {
      this.ficheros = data;
    })



    this.usuario = this.authService.currentUserValue;
    this.usuario.peticionesSoporteFront++;

    this.authService.updateUser(this.usuario);




  }


  handleFileInput(files: FileList){

    this.fichero = files.item(0);

    var ext = this.fichero.name.substring(this.fichero.name.lastIndexOf('.') + 1);

    if (ext.toLowerCase() != 'txt') {
      alert("Fichero con extensión no válida.\nSolo válidos ficheros .txt.")
      this.fileInput.nativeElement.value = "";
    }
  }




  eliminarFichero(fichero: Fichero) {

    this.ficheroService.delete(fichero.fid)
    .subscribe(data => {


      this.usuario = this.authService.currentUserValue;

      this.usuario.peticionesSoporteFront++;
      this.usuario.ficherosAlmacenadosActualmente--;

      this.authService.updateUser(this.usuario);

      alert("Fichero " + fichero.fileName + " eliminado correctamente");

      location.reload();
    });
  }



  verContenidoFichero(fichero: Fichero) {

    this.ficheroService.setFid(fichero.fid);
    this.router.navigate(['/fichero'])

  }


  uploadFile() {
    this.ficheroService.post(this.fichero)
    .subscribe(data => {

      this.usuario = this.authService.currentUserValue;

      this.usuario.peticionesSoporteFront++;
      this.usuario.ficherosAlmacenadosActualmente++;
      this.usuario.ficherosSubidosTotalmente++;

      this.authService.updateUser(this.usuario);

      alert("Fichero subido correctamente")
      location.reload();
    },
    error => {

      if (error.status == 409) {
        alert("Ya existe un fichero con el nombre '" + this.fichero.name + "'")
      } else {
        alert("El fichero no se ha podido subir")
      }
      this.fileInput.nativeElement.value = "";
    })
  }

  entrenar(fichero: Fichero) {


    this.entrenamientoService.entrenar(fichero.fid)
    .subscribe(data => {

      this.usuario = this.authService.currentUserValue;
      this.usuario.peticionesSoporteFront++;
      this.usuario.peticionesWord2VecTrain++;
      this.usuario.entrenamientosHechos++;

      this.authService.updateUser(this.usuario);
      //alert("Fichero entrenado correctamente")

      location.reload();

    }, error => {

    })

  }


  entrenado(fichero: Fichero) {
    return fichero.hayEntrenamiento != 'false';
  }

  entrenando(fichero: Fichero) {
    return fichero.tid != "";
  }

  comprobar(fichero: Fichero) {


    this.ficheroService.get(fichero.fid).subscribe(data => {

      let cambiado = false;
      let i = 0;
      while (i < this.ficheros.length && !cambiado) {
        if (this.ficheros[i].fid == fichero.fid) {
          this.ficheros[i] = data;
          alert("El entrenamiento se ha realizado correctamente");
          cambiado = true;

        }
      }


      this.usuario = this.authService.currentUserValue;
      this.usuario.peticionesSoporteFront++;

      this.authService.updateUser(this.usuario);
    })
  }

}
