import {Fetcher} from "../resources/util/fetcher";
import {I18N} from 'aurelia-i18n';

export class Filter {
  static inject = [I18N];
  constructor(i18n) {
    this.i18n = i18n;
  }

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
      var endpoint = `menus/filter/${maxPrice}/?lang=${this.i18n.getLocale()}`;
      Fetcher.getInstance()
        // don't remove any slashes
        .fetch(endpoint, {'method': 'GET'})
        .then(response => response.json())
        .then(data => this.data = data)
        .then(() => this.checkHasResults())
        .then(() => this.loading = false);
    } else {
      this.hasResults = false;
    }
  }
}
