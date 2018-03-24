import {Fetcher} from '../util/fetcher';

export class Menu {
  constructor() {
    this.data = null;
    this.successfulRequest = false;
  }

  activate(params) {
    this.getMenuItems(params.id);
  }

  getMenuItems(foodServiceId) {
    Fetcher.getInstance()
      .fetch(`foodservices/${foodServiceId}/menus/today`, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
      .then(() => this.successfulRequest = true);
  }
}
