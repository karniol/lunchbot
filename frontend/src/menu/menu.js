import {Fetcher} from "../util/fetcher";

export class Menu {
  constructor() {
    this.data = null;
    this.successfulRequest = false;
  }

  activate(params) {
    this.getMenuItems(params.id);
  }

  static formatPriceString(data) {
    data["menu_items"].forEach(function(menuItem) {
      menuItem["price_string"] = "€" + menuItem["price"].toFixed(2);
    });
    return data
  }

  getMenuItems(foodServiceId) {
    Fetcher.getInstance()
      .fetch(`foodservices/${foodServiceId}/menus/today`, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
      .then(() => Menu.formatPriceString(this.data))
      .then(() => this.successfulRequest = true);
  }
}
