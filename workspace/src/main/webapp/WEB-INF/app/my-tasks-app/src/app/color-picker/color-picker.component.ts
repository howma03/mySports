import {Component, OnInit, Input, Output, EventEmitter} from "@angular/core";
import {BLUE, RED} from "./constants";

@Component({
  selector: 'color-picker',
  templateUrl: './color-picker.component.html',
  template: `
    
    <div class="color-title" [ngStyle]="{color:color}">Pick a Color:</div>  
    
    <div class="color-picker">
        <div class="color-sample color-sample-blue" (click)="choose('${BLUE}')"></div>  
        <div class="color-sample color-sample-red" (click)="choose('${RED}')"></div>                
    </div>
    <button (click)="reset()">Reset</button>
    `,
  styleUrls: ['./color-picker.component.css']
})
export class ColorPickerComponent implements OnInit {

  @Input()
  color: string;

  @Output("color")
  colorOutput = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

  choose(color: string) {
    console.log("color-picker - Color clicked .. color=" + color);
    this.colorOutput.emit(color);
  }

  reset() {
    console.log("color-picker - Color reset");
    this.colorOutput.emit("black");
  }
}
