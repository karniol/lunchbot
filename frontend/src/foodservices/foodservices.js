import {Fetcher} from "../util/fetcher";


export class FoodServices {
  constructor() {
    this.successfulRequest = false;
    this.data = null;
  }

  activate(params) {
    this.getFoodServiceNames();
  }

  getFoodServiceNames() {
    Fetcher.getInstance()
      .fetch(`foodservices`, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
      .then(() => this.successfulRequest = true);
  }
}
