import {Fetcher} from "../resources/util/fetcher";

export class Filter {
  constructor() {}

  activate(params) {
    this.data = null;
    this.loading = true;
    this.getMenuItems(params.price);
  }

  getMenuItems(maxPrice) {
    Fetcher.getInstance()
      // don't remove any slashes
      .fetch(`menus/filter/${maxPrice}/`, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
      .then(() => this.loading = false);
  }
}
