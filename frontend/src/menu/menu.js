import {Fetcher} from "../resources/util/fetcher";

export class Menu {
  constructor() {}

  activate(params) {
    this.data = null;
    this.loading = true;
    this.getMenuItems(params.id);
  }

  getMenuItems(foodServiceId) {
    Fetcher.getInstance()
      .fetch(`foodservices/${foodServiceId}/menus/today/`, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
      .then(() => this.loading = false);
  }
}
