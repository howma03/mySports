import {Injectable} from "@angular/core";
import {Http, Response, Headers, RequestOptions, RequestMethod, Request} from "@angular/http";
import {Observable} from "rxjs";
import {Task} from "./task";

const DEFAULT_TASKS = [
  {id: 1, name: "Something"},
  {id: 2, name: "Else"},
  {id: 3, name: "Task 3"},
  {id: 4, name: "Task 5"},
  {id: 5, name: "Another Task"}
];

@Injectable()
export class TaskApiService {
  private tasksUrl = 'http://localhost:4200/api/task/';
  private tasks = [];

  // TS shortcut "public" to put http on this
  constructor(private http: Http) {
    this.readTasks();
  }

  /**
   * Create a task
   * @param name
   * @returns {TaskApiService}
   */
  createTask(name): TaskApiService {
    console.log("Creating task name='" + name + "'");
    const task = {name};
    this.tasks.push(task);

    let body = JSON.stringify(task);
    let headers = new Headers({'Content-Type': 'application/json'});

    console.log("Performing PUT - url=" + this.tasksUrl + " body=" + body);

    let requestOptions = new RequestOptions({
      method: RequestMethod.Post,
      url: this.tasksUrl,
      headers: headers,
      body: body
    });

    let useNew = true;
    if (useNew) {
      this.http.request(new Request(requestOptions))
        .catch((error: any) => Observable.throw(error.json().error || 'Server error'))
        .map((res: Response) => {
          if (res) {
            return [{status: res.status, json: res.json()}]
          }
        })
        .subscribe(
          () => {
            console.log("Task created");
          },
          err => console.log("error - " + err)
        );
    }
    else {
      let options = new RequestOptions({headers: headers});
      this.http.post(this.tasksUrl, body, headers)
        .catch((error: any) => Observable.throw(error.json().error || 'Server error'))
        .map(res => res.json().data)
        .subscribe(
          () => {
            console.log("Task created");
          },
          err => console.log("error - " + err)
        );
    }

    return this;
  }

  /**
   * READ-ALL Operation
   */
  readTasks() {
    this.tasks = DEFAULT_TASKS;
    console.log("Reading Tasks - url=" + this.tasksUrl);
    this.http.get(this.tasksUrl)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'))
      .subscribe(
        tasksData => this.tasks = tasksData
      );
  }

  /**
   * READ Operation
   */
  readTask(id) {
    return this.tasks
      .filter(todo => todo.id === id)
      .pop();
  }

  updateTask(id: number, values: Object = {}): Task {
    let task = this.readTask(id);
    if (!task) {
      return null;
    }
    Object.assign(task, values);
    // POST The Task to the Server
    return task;
  }

  deleteTask() {
  }

}
