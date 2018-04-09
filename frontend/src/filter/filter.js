import {Fetcher} from "../resources/util/fetcher";

export class Filter {
  constructor() {}

  activate(params) {
    this.data = null;
    this.loading = true;
    this.hasResults = true;
    this.getMenuItems(params.price);
  }
  checkHasResults() {
    if (this.data === null) {
      this.hasResults = false;
    } else {
      this.hasResults = true;
    }
  }

  getMenuItems(maxPrice) {
    var value = parseFloat(maxPrice.replace(",", "."));
    if (!isNaN(value) && value.toFixed(2).toString().indexOf(".") != -1) {
      Fetcher.getInstance()
        // don't remove any slashes
        .fetch(`menus/filter/${maxPrice}/`, {'method': 'GET'})
        .then(response => response.json())
        .then(data => this.data = data)
        .then(() => this.checkHasResults())
        .then(() => this.loading = false);
    } else {
      this.hasResults = false;
    }
  }
}
