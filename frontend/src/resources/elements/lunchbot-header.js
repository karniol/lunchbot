export class LunchbotHeader {
  constructor() {
    this.searchMenuVisible = false;
  }

  toggleSearchMenu() {
    this.searchMenuVisible = !this.searchMenuVisible;
  }
}
