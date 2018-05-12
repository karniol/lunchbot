import environment from "./environment";
import {I18N} from 'aurelia-i18n';

export class App {
  static inject = [I18N];
  constructor(i18n) {
    this.i18n = i18n;
  }

  configureRouter(config, router) {
    this.router = router;
    config.title = "Lunchbot";
    config.options.pushState = true;
    config.options.root = "/";

    config.map([
      { route: "/",
        moduleId: "./foodservices/foodservices",
        name: "foodservices",
        nav: 0
      },

      { route: "/menu/:id",
        href: "#id",
        moduleId: "./menu/menu",
        name: "menu",
        nav: true
      },

      { route: "/filter/:price",
        href: "#price",
        moduleId: "./filter/filter",
        name: "filter",
        nav: true
      }
    ]);

    config.mapUnknownRoutes("not-found/not-found");

    // Scroll to top when displaying new route
    config.addPostRenderStep({
      run(navigationInstruction, next) {
        if (navigationInstruction.router.isNavigatingNew) {
          window.scroll(0, 0);
        }

        return next();
      }
    });
  }
}
