import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DefaultUpload } from '../../components/default-upload/default-upload';
import { MenuComponent } from '../../components/menu/menu';
import { FooterComponent } from '../../components/footer/footer';
import { Router } from '@angular/router';
import { AnaliseService } from '../../services/AnaliseService';


@Component({
  selector: 'app-main-screen',
  standalone: true,
  imports: [ReactiveFormsModule, DefaultUpload, MenuComponent, FooterComponent],
  templateUrl: './main-screen.html',
  styleUrl: './main-screen.scss',
})
export class MainScreen {
  
  form!: FormGroup;
  porcentagemEnvio = 0;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private analiseService: AnaliseService
  ) {
    this.form = this.fb.group({
      upload_proposta: [null, Validators.required],
      upload_licitacao: [null, Validators.required],
    });
  }

  compararDocumentos() {
    this.porcentagemEnvio = 0;

    const intervalo = setInterval(() => {
      if (this.porcentagemEnvio >= 100) {
        clearInterval(intervalo);

        //Simula dados da análise (depois trocar pelo backend)
        this.analiseService.similaridades = "Trecho em comum X, Y, Z...";
        this.analiseService.diferencas = "Trecho A diferente de B...";
        this.analiseService.porcentagem = 72;

        this.router.navigate(['/resultados']);
        return;
      }

      this.porcentagemEnvio += 10;
    }, 300);
  }
}

//quando a função fileValidator é chamada, o angular automaticamente passa o campo que possui o formControl como parametro

//   fileValidator(arq : any){
//     const file = arq?.value

//     if(!file || file.length === 0){
//       return {fileRequired: true} //erro de validação
//     }

//     if(file.length > 1)

//   }
//
