import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'app/Auth/authentication.service';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent implements OnInit {


  constructor(private authService: AuthenticationService, private router: Router) {}

  ngOnInit(): void {

    if (this.authService.currentUserValue == null ) {
      this.router.navigate(['/login']);
    }






  }

}
