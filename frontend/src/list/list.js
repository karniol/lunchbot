export class List {
  constructor() {
    this.fakeRestaurantList = [
      "Peahoone lõunarestoran",
      "Peahoone kohvik",
      "Raamatukogu kohvik",
      "Sotsiaalteaduste maja söökla",
      "Neljanda korpuse kohvik",
      "Kuuenda korpuse kohvik-söökla",
      "Loodusteaduste maja kohvik",
      "IT-maja kohvik",
      "Spordihoone kohvik"
    ];

    this.styleClasses = {
      salmon: 0,
      ocean: 0,
      moon: 0,
      forest: 0
    };

    this.styleClassKeys = Object.keys(this.styleClasses);

    this.lastStyleClass = null;
  }

  randomStyleClass() {
    let styleClass = this.styleClassKeys[Math.floor(Math.random() * this.styleClassKeys.length)];

    while (styleClass === this.lastStyleClass) {
      styleClass = this.styleClassKeys[Math.floor(Math.random() * this.styleClassKeys.length)];
    }

    this.lastStyleClass = styleClass;
    return styleClass;
  }
}
