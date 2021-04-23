export class Usuario {

  id: String;
  user: String;
  password: String;
  nombre: String;
  apellidos: String;

	ficherosAlmacenadosActualmente: number;
  ficherosSubidosTotalmente: number;
  vecesConectado: number;

  peticionesSoporteFront: number;
  peticionesWord2VecTrain: number;
  peticionesWord2VecUse: number;

  vecesContrasenaModificada: number;

  palabrasConsultadas: number;

  entrenamientosHechos: number;


}
