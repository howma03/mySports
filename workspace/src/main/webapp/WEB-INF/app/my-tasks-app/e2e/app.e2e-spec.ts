import {MyTasksAppPage} from "./app.po";

describe('my-tasks-app App', () => {
  let page: MyTasksAppPage;

  beforeEach(() => {
    page = new MyTasksAppPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
