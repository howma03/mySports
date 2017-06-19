import {Directive, ContentChildren, QueryList} from "@angular/core";
import {ShowOneTriggerDirective} from "./show-one-trigger.directive";
import {ShowOneDirective} from "./show-one.directive";

@Directive({
  selector: '[showOneContainerDirective]'
})
export class ShowOneContainerDirective {

  triggers: ShowOneTriggerDirective[] = [];

  @ContentChildren(ShowOneDirective)
  items: QueryList<ShowOneDirective>;

  constructor() {

  }

  add(trigger: ShowOneTriggerDirective) {
    this.triggers.push(trigger);
  }

  show(id: string) {

  }
}
