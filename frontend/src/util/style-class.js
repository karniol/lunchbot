// StyleClass is a singleton for assigning style classes

export class StyleClass {
  constructor() {
    this.styleClasses = {
      salmon: 0,
      ocean: 0,
      moon: 0,
      forest: 0
    };
    this.styleClassKeys = Object.keys(this.styleClasses);
    this.lastStyleClass = null;
  }

  random() {
    let styleClass = this.styleClassKeys[Math.floor(Math.random() * this.styleClassKeys.length)];

    while (styleClass === this.lastStyleClass) {
      styleClass = this.styleClassKeys[Math.floor(Math.random() * this.styleClassKeys.length)];
    }

    this.lastStyleClass = styleClass;
    return styleClass;
  }

}

export var StyleClassSingleton = (function () {
  let instance;

  function createInstance() {
    return StyleClass();
  }

  return {
    getInstance: function () {
      if (!instance) {
        instance = createInstance();
      }
      return instance;
    }
  }
})();
