import {Component, OnInit, Input} from "@angular/core";

@Component({
  selector: 'app-color-sample',
  templateUrl: './color-sample.component.html',
  styleUrls: ['./color-sample.component.css']
})
export class ColorSampleComponent implements OnInit {

  @Input()
  color: string;

  constructor() {
  }

  ngOnInit() {
  }

}
