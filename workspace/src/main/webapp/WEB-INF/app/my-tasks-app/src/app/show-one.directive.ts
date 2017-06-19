import {Directive, Input, HostBinding} from "@angular/core";

@Directive({
  selector: '[showOneDirective]'
})
export class ShowOneDirective {

  constructor() {
  }

  @Input("showOne")
  id: string;

  @Input("active")
  active = false;

  @HostBinding('hidden')
  get hidden() {
    return !this.active;
  }

}
