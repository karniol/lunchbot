import {Fetcher} from "../resources/util/fetcher";

export class FoodServices {
  constructor() {}

  activate(params) {
    this.data = null;
    this.loading = true;
    this.getFoodServiceNames();
  }

  getFoodServiceNames() {
    Fetcher.getInstance()
      .fetch(`foodservices`, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
      .then(() => this.loading = false);
  }
}
