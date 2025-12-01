import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './services/auth-guard.service';
import { MainScreen } from './pages/main-screen/main-screen';
import { ResultadoAnaliseComponent } from './pages/resultado/resultado';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  { path: 'main', component: MainScreen, canActivate: [AuthGuard] },

  {
    path: 'resultados',
    component: ResultadoAnaliseComponent,
    canActivate: [AuthGuard],
  },

  { path: '', redirectTo: '/login', pathMatch: 'full' },

  { path: '**', redirectTo: '/login' },
];
