import {Component} from "@angular/core";
import {TaskApiService} from "./task-api.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app works!';

  constructor(private taskService: TaskApiService) {

  }

  onColor(color) {
    console.log("App - Color:", color);
  }

  reset() {
    console.log("App - Reset");
  }

}
