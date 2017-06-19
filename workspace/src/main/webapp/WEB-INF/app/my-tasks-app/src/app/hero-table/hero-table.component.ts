import {Component, OnInit} from "@angular/core";

const HEROES = [
  {id: 1, name: "Superman"},
  {id: 2, name: "Batman"},
  {id: 3, name: "BatGirl"},
  {id: 4, name: "Robin"},
  {id: 5, name: "Flash"}
];

@Component({
  selector: 'app-hero-table',
  templateUrl: './hero-table.component.html',
  styleUrls: ['./hero-table.component.css']
})
export class HeroTableComponent implements OnInit {

  heroes = HEROES;

  constructor() {
  }

  ngOnInit() {
  }

}
