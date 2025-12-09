import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";


@Injectable({
  providedIn: 'root'
})
export class AnaliseService {

  similaridades: string = "";
  diferencas: string = "";
  porcentagem: number = 0;

  constructor(private http: HttpClient) { }

  enviarArquivos(formData: FormData) {
    return this.http.post<any>("http://localhost:8080/api/analise/enviar", formData);
  }
}
