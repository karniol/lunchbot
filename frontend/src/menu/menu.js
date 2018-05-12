import {Fetcher} from "../resources/util/fetcher";
import {I18N} from 'aurelia-i18n';

export class Menu {
  static inject = [I18N];
  constructor(i18n) {
    this.i18n = i18n;
  }

  activate(params) {
    this.data = null;
    this.loading = true;
    this.getMenuItems(params.id);
  }

  getMenuItems(foodServiceId) {
    var endpoint = `foodservices/${foodServiceId}/menus/today?lang=${this.i18n.getLocale()}`;
    Fetcher.getInstance()
      .fetch(endpoint, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
      .then(() => this.loading = false);
  }
}
