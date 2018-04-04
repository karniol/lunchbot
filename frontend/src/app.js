import environment from "./environment";

export class App {
  constructor() {}

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
  }
}
