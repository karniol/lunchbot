import environment from "./environment";

export class App {
  constructor() {}

  toggleSearchMenu() {
    document.getElementById("lunchbot-id-search-menu").classList.toggle("d-none");
  }

  configureRouter(config, router) {
    config.title = 'Lunchbot';
    config.options.pushState = true;
    config.options.root = '/';

    config.map([
      { route: '/menu/:id',
        moduleId: './menu/menu',
        name: "menu",
        nav: true,
        href: '#id'
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
