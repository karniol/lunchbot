export class MenuOverviewBar {
  constructor() {
    this.overviewVisible = false;
  }

  openOverview() {
    this.overviewVisible = true;
    document.getElementById("lunchbot-menu-overview").classList.remove("slideOutUp");
    document.getElementById("lunchbot-menu-overview").classList.add("slideInDown");
  }

  closeOverview() {
    this.overviewVisible = false;
    document.getElementById("lunchbot-menu-overview").classList.remove("slideInDown");
    document.getElementById("lunchbot-menu-overview").classList.add("slideOutUp");
  }

  toggleOverview() {
    if (this.overviewVisible) {
      this.closeOverview();
    } else {
      this.openOverview();
    }
  }

  activate(model) {
    this.data = model;
  }
}
