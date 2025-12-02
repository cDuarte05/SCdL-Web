import { Component } from '@angular/core';
import { AnaliseService } from '../../services/AnaliseService';

@Component({
  selector: 'app-resultado-analise',
  standalone: true,
  templateUrl: './resultado.html',
  styleUrls: ['./resultado.scss']
  
})
export class ResultadoAnaliseComponent {

  constructor(public analiseService: AnaliseService) {}

  get similaridades() {
    return this.analiseService.similaridades;
  }

  get diferencas() {
    return this.analiseService.diferencas;
  }

  get porcentagem() {
    return this.analiseService.porcentagem;
  }
}
