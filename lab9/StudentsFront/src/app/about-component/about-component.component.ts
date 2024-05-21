import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-about-component',
  templateUrl: './about-component.component.html',
  styleUrls: ['./about-component.component.css']
})
export class AboutComponentComponent implements OnInit {

  date: string = new Date(Date.now()).toString();

  constructor() { }

  ngOnInit(): void {
    this.date = new Date(Date.now()).toString();
  }

}
