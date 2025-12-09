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
    if (!this.form.valid) {
      return;
    }

    const formData = new FormData();
    formData.append("upload_proposta", this.form.get("upload_proposta")!.value);
    formData.append("upload_licitacao", this.form.get("upload_licitacao")!.value);

   this.analiseService.enviarArquivos(formData).subscribe({
  next: (res: any) => {
    this.analiseService.similaridades = res.semelhancas;
    this.analiseService.diferencas = res.diferencas;
    this.analiseService.porcentagem = res.nota;
  },
  error: (err: any) => {
    console.error("Erro:", err);
  }
});

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
