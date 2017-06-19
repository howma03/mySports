import {Component, OnInit, Input} from "@angular/core";

@Component({
  selector: 'task-search-box',
  templateUrl: './task-search-box.component.html',
  styleUrls: ['./task-search-box.component.css']
})
export class TaskSearchBoxComponent implements OnInit {

  @Input('placeholderText')
  placeholder = "Type in your search";

  constructor() {
  }

  ngOnInit() {
  }

  find(input) {
    console.log("Find clicked .. input=" + input);
  }

  clear(input) {
    console.log("Clear clicked .. input=" + input);
    input.value = '';
  }

}
