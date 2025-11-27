import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DefaultUpload } from '../../components/default-upload/default-upload';

@Component({
  selector: 'app-main-screen',
  standalone: true,
  imports: [ReactiveFormsModule, DefaultUpload],
  templateUrl: './main-screen.html',
  styleUrl: './main-screen.scss',
})
export class MainScreen {
  
  form!: FormGroup;
  porcentagemEnvio = 0;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      upload_proposta: [null, Validators.required],
      upload_licitacao: [null, Validators.required],
    });
  }

  onPropostaSelected(file: File) {
    this.form.get;
  }

  compararDocumentos() {
    this.porcentagemEnvio = 0;

    const intervalo = setInterval(() => {
      if (this.porcentagemEnvio >= 100) {
        clearInterval(intervalo);
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
