import { Component, Input, input } from '@angular/core';

@Component({
  selector: 'app-default-upload',
  imports: [],
  standalone: true,
  templateUrl: './default-upload.html',
  styleUrl: './default-upload.scss',
})
export class DefaultUpload {

  @Input() type : string = "file";
  @Input() label : string = "";

}
