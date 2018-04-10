import {Router} from "aurelia-router";

export class LunchbotHeader {
  static inject() { return [Router]; }

  constructor(router) {
    this.router = router;
    this.searchMenuVisible = false;
  }

  toggleSearchMenu() {
    this.searchMenuVisible = !this.searchMenuVisible;
  }

  search() {
    let value = document.getElementById("headerFormInputPrice").value;
    if (value) {
      this.router.navigate(`/filter/${value}/`);
      this.searchMenuVisible = false;
    }

  }
}
