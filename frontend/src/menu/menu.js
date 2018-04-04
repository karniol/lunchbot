import {Fetcher} from "../resources/util/fetcher";

export class Menu {
  constructor() {}

  activate(params) {
    this.data = null;
    this.loading = true;
    this.getMenuItems(params.id);
  }

  static formatPriceString(data) {
    data["menu_items"].forEach(function(menuItem) {
      menuItem["price_string"] = "€" + menuItem["price"].toFixed(2);
    });
    return data;
  }

  getMenuItems(foodServiceId) {
    Fetcher.getInstance()
      .fetch(`foodservices/${foodServiceId}/menus/today`, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
      .then(() => Menu.formatPriceString(this.data))
      .then(() => this.loading = false);
  }

  openSidebar() {
    let sidebar = document.getElementById("sidebar");
    sidebar.style.visibility = "visible";
  }

  closeSidebar() {
    let sidebar = document.getElementById("sidebar");
    sidebar.style.visibility = "hidden";
  }
}
