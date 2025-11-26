import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-default-upload',
  standalone: true,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR, //Ou seja, esse componente conversa com ANgular Forms
      useExisting: forwardRef(() => DefaultUpload),
      multi: true,
    },
  ],
  templateUrl: './default-upload.html',
  styleUrl: './default-upload.scss',
})
export class DefaultUpload implements ControlValueAccessor {
  @Input() label: string = '';
  @Input() accept: string = '*/*';
  @Input() inputName: string = '';

  file?: File | null;

  triggerFileInput(input: HTMLInputElement) {
    input.click();
  }
  //Essas duas variaveis são funções vazias definidas para não dar erro quando forem chamadas
  onChange = (file: File | null) => {};
  //mesmo que vá ser substituido pelo fn, isso acontece em tempo de execução,
  // sendo assim o ts não consegue prever e sem esses parametros, acabaria quebrando na parte do onfileChange

  onTouched = () => {};

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    const selectedFile = input.files?.[0] || null;

    this.file = selectedFile;
    this.onChange(selectedFile); //envia o arquivo para o angular guardar no formControl do pai
    this.onTouched();
  }

  writeValue(file: File | null): void {
    this.file = file;
  }

  //Esses fn são funções que o proprio angular manda para nos quando se implementa aquela interface
  //E essas funções ficam armazenadas nesses dois atributos que criei, sendo assim,
  // quando algum item mudar, eu chamo esses atributos quando o item é selecionado no metodo onFileChange
  //Esse fn passa sempre espera um parametro
  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(disabled: boolean): void {}
}

/*componente padrão pro que vai ser usado em cada um dos botões de upload
quando este componente for chamado no componente pai (main-screen), lá que os 
parametros vão ser passados para que a tela parte do botão possa ser montada
*/
