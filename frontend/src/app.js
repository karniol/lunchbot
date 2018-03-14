export class App {
  constructor() {}

  configureRouter(config, router) {
    config.title = 'Lunchbot';
    config.options.pushState = true;
    config.options.root = '/';

    config.map([
      { route: '/menu',
        moduleId: './menu/menu',
        name: "menu",
        nav: true
      },

      { route: '/',
        moduleId: './foodservices/foodservices',
        name: "foodservices",
        nav: true
      },
    ]);

    this.router = router;
  }
}
