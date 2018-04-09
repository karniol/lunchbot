export class LunchbotHeader {
  constructor() {
    this.searchMenuVisible = false;
  }

  activate(params) {
    document.getElementById('email').onkeydown = function(e) {
      if (e.keyCode == 13) {
        console.log('sup');
      }
    };
  }

  toggleSearchMenu() {
    this.searchMenuVisible = !this.searchMenuVisible;
  }

  search() {
    let value = document.getElementById("headerFormInputPrice").value;
    window.location.href = `/filter/${value}/`; // TODO: with routing
    this.searchMenuVisible = false;
  }
}
