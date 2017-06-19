import {Directive, Input, HostListener} from "@angular/core";
import {ShowOneContainerDirective} from "./show-one-container.directive";

@Directive({
  selector: '[showOneTriggerDirective]'
})
export class ShowOneTriggerDirective {

  constructor(private showOneContainer: ShowOneContainerDirective) {
    showOneContainer.add(this);
  }

  @Input("showOneTrigger")
  id: string;

  @Input()
  active = false;

  @HostListener('click')
  click() {
    console.log("Clicked!");
    this.showOneContainer.show(this.id);
  }
}
