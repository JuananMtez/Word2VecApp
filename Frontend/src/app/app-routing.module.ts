import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CodeConfirmationComponent } from './Auth/code-confirmation/code-confirmation.component';
import { LoginComponent } from './Auth/login/login.component';
import { RegisterComponent } from './Auth/register/register.component';
import { EstadisticasComponent } from './Estadisticas/estadisticas/estadisticas.component';
import { GetFicheroComponent } from './Fichero/get-fichero/get-fichero.component';
import { ListarFicherosComponent } from './Fichero/listar-ficheros/listar-ficheros.component';
import { InicioComponent } from './Inicio/inicio/inicio.component';
import { ModificarUsuarioComponent } from './Usuario/modificar-usuario/modificar-usuario.component';
import { PerfilComponent } from './Usuario/perfil/perfil.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'inicio', component: InicioComponent},
  { path: 'ficheros/listar', component: ListarFicherosComponent},
  { path: 'fichero', component: GetFicheroComponent},
  { path: 'usuario/modificar', component: ModificarUsuarioComponent},
  { path: 'usuario/estadisticas', component: EstadisticasComponent},
  { path: 'usuario/perfil', component: PerfilComponent},
  { path: 'register/code', component: CodeConfirmationComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
