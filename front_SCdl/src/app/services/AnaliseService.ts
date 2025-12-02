import { Injectable } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class AnaliseService {
  similaridades = '';
  diferencas = '';
  porcentagem = 0;
}
