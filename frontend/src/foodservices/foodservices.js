import {Fetcher} from '../util/fetcher';

export class FoodServices {
  constructor() {
    // Style class identifiers, see src/resources/style/default.scss
    this.styleClasses = [1, 2, 3, 4];

    this.successfulRequest = false;
    this.data = null;
    this.getFoodServiceNames();
  }

  getFoodServiceNames() {
    Fetcher.getInstance()
      .fetch(`foodservices`, {'method': 'GET'})
      .then(response => response.json())
      .then(data => this.data = data)
      .then(() => this.successfulRequest = true);
  }
}
