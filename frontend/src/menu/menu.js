import {Fetcher} from '../util/fetcher';

export class Menu {
  constructor() {
    this.cafeId = 1;
    this.data = null;
    this.getMenuItems();
  }

  getMenuItems() {
    Fetcher.getInstance()
      .fetch(`cafes/${this.cafeId}/menu`, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data);
  }
}
