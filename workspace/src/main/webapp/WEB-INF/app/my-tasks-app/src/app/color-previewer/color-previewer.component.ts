import {Component, OnInit, Input} from "@angular/core";

@Component({
  selector: 'app-color-previewer',
  templateUrl: './color-previewer.component.html',
  styleUrls: ['./color-previewer.component.css']
})
export class ColorPreviewerComponent implements OnInit {

  @Input('color')
  color: string;

  constructor() {
  }

  ngOnInit() {
  }

}
