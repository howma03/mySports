import {Task} from "./task";

describe('Task', () => {
  it('should create an instance', () => {
    expect(new Task()).toBeTruthy();
  });

  it('should accept values in the constructor', () => {
    let todo = new Task({
      name: 'hello',
      complete: true
    });
    expect(todo.name).toEqual('hello');
    expect(todo.complete).toEqual(true);
  });

});
