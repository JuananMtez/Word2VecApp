import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './Auth/login/login.component';
import { RegisterComponent } from './Auth/register/register.component';
import { AuthenticationService } from './Auth/authentication.service';
import { EncabezadoComponent } from './Encabezado/encabezado/encabezado.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UsuarioService } from './Usuario/usuario.service';
import {HttpClientModule} from '@angular/common/http';
import { InicioComponent } from './Inicio/inicio/inicio.component';
import { ListarFicherosComponent } from './Fichero/listar-ficheros/listar-ficheros.component';
import { GetFicheroComponent } from './Fichero/get-fichero/get-fichero.component';
import { ModificarUsuarioComponent } from './Usuario/modificar-usuario/modificar-usuario.component';
import { FicheroService } from './Fichero/fichero.service';
import { EstadisticasComponent } from './Estadisticas/estadisticas/estadisticas.component';
import { PerfilComponent } from './Usuario/perfil/perfil.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    EncabezadoComponent,
    InicioComponent,
    ListarFicherosComponent,
    GetFicheroComponent,
    ModificarUsuarioComponent,
    EstadisticasComponent,
    PerfilComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    MatToolbarModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [
    AuthenticationService,
    UsuarioService,
    FicheroService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
