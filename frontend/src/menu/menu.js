import {HttpClient} from 'aurelia-fetch-client';

export class Menu {
  constructor() {
    this.message = 'Menu';
    this.cafeId = 1;
    this.data = null;
    this.getMenuItems();
  }

  getMenuItems() {
    let httpClient = new HttpClient();

    httpClient
      .fetch(`http://localhost:8080/cafes/${this.cafeId}/menu`, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
  }
}
