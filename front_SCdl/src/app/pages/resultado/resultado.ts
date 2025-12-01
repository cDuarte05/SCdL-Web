import { Component, Input } from '@angular/core';
import { MenuComponent } from "../../components/menu/menu";
import { FooterComponent } from "../../components/footer/footer";

@Component({
  selector: 'app-resultado-analise',
  standalone: true,
  templateUrl: './resultado.html',
  styleUrls: ['./resultado.scss'],
  imports: [MenuComponent, FooterComponent]
})
export class ResultadoAnaliseComponent {

  @Input() similaridades: string = '';
  @Input() diferencas: string = '';
  @Input() porcentagem: number = 0;

}
