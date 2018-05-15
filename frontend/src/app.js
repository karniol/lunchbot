import environment from "./environment";
import {I18N} from 'aurelia-i18n';
import {activationStrategy} from 'aurelia-router';

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
        nav: true,
        activationStrategy: activationStrategy.invokeLifecycle
      },

      { route: "/menu/:id",
        href: "#id",
        moduleId: "./menu/menu",
        name: "menu",
        nav: true,
        activationStrategy: activationStrategy.invokeLifecycle
      },

      { route: "/filter/:price",
        href: "#price",
        moduleId: "./filter/filter",
        name: "filter",
        nav: true,
        activationStrategy: activationStrategy.invokeLifecycle
      },

      { route: "/vegetarian",
        moduleId: "./vegetarian/vegetarian",
        name: "vegetarian",
        nav: true,
        activationStrategy: activationStrategy.invokeLifecycle
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
