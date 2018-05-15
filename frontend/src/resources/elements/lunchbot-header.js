import {Router} from "aurelia-router";
import {I18N} from 'aurelia-i18n';

export class LunchbotHeader {
  static inject() { return [Router, I18N]; }

  constructor(router, i18n) {
    this.router = router;
    this.i18n = i18n;
    this.searchMenuVisible = false;
  }

  switchLanguage() {
    if (this.i18n.getLocale() == "et") {
      this.i18n.setLocale("en");
    } else if (this.i18n.getLocale() == "en") {
      this.i18n.setLocale("et");
    }
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

  route(path) {
    this.router.navigate(path);
  }
}
