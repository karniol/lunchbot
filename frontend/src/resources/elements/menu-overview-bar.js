export class MenuOverviewBar {
  constructor() {
    this.overviewVisible = false;
  }

  openOverview(id) {
    this.overviewVisible = true;
    document.getElementById(`lunchbot-menu-overview-${id}`).classList.remove("slideOutUp");
    document.getElementById(`lunchbot-menu-overview-${id}`).classList.add("slideInDown");
  }

  closeOverview(id) {
    // TODO: Closes instantly, see also menu-overview-bar.html
    document.getElementById(`lunchbot-menu-overview-${id}`).classList.remove("slideInDown");
    document.getElementById(`lunchbot-menu-overview-${id}`).classList.add("slideOutUp");
    this.overviewVisible = false;
  }

  toggleOverview(foodServiceId) {
    if (this.overviewVisible) {
      this.closeOverview(foodServiceId);
    } else {
      this.openOverview(foodServiceId);
    }
  }

  activate(model) {
    this.data = model;
  }
}
