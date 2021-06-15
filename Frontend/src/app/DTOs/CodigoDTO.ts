export class CodigoDTO {

  codigoConfirmacion: string;
  correoElectronico: string;

  constructor(email:string) {
    this.codigoConfirmacion = "";
    this.correoElectronico = email;
  }
}


