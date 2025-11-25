import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-default-upload',
  imports: [],
  standalone: true,
  templateUrl: './default-upload.html',
  styleUrl: './default-upload.scss',
})
export class DefaultUpload {
  
  @Input() label: string = ''; 
  @Input() type : string = "file";
  @Input() accept : string = "";
  @Output() fileSelected = new EventEmitter<File>();


  //manipula o arq
  onFileSelected(event: Event): void{
    const input = event.target as HTMLInputElement;
    if(input.files && input.files.length > 0){
      const file = input.files[0];
      this.fileSelected.emit(file);
    }
  }

  //função para ativar o seletor de arquivos
  triggerFileInput(fileInput: HTMLInputElement): void{
    fileInput.click();
  }
}

/*componente padrão pro que vai ser usado em cada um dos botões de upload
quando este componente for chamado co componente pai (main-screen), lá que os 
parametros vão ser passados para que a tela parte do botão possa ser montada
*/
/* O output serve para que a classe filho possa ser o mais generica possivel
sem a implantação de nenhuma regra de negocio, service nem nada do genero.
sendo assim, quando o arquivo é selecionado, ele é emitido  pro pai 
*/