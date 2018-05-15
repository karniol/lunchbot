import {Fetcher} from "../resources/util/fetcher";
import {I18N} from 'aurelia-i18n';

export class Vegetarian {
  static inject = [I18N];
  constructor(i18n) {
    this.i18n = i18n;
  }

  activate(params) {
    this.data = null;
    this.loading = true;
    this.hasResults = true;
    this.getMenuItems();
  }

  checkHasResults() {
    if (this.data === null) {
      this.hasResults = false;
    } else {
      this.hasResults = true;
    }
  }

  getMenuItems() {
    var endpoint = `menus/vege?lang=${this.i18n.getLocale()}`;
    Fetcher.getInstance()
      .fetch(endpoint, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
      .then(() => this.checkHasResults())
      .then(() => this.loading = false);
  }
}
