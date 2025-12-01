import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { MenuComponent } from "./components/menu/menu";
import { FooterComponent } from "./components/footer/footer";
import { LoginService } from './services/login.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, MenuComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  constructor(private loginService: LoginService) {}

  get isLoggedIn(): boolean {
    return this.loginService.isLoggedIn();
  }
}
