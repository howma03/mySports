import {ShowOneTriggerDirective} from "./show-one-trigger.directive";
import {ShowOneContainerDirective} from "./show-one-container.directive";

describe('ShowOneTriggerDirective', () => {
  it('should create an instance', () => {
    const directive = new ShowOneTriggerDirective(new ShowOneContainerDirective());
    expect(directive).toBeTruthy();
  });
});
