export class App {
  configureRouter(config, router) {
    config.title = '';
    config.map([
      { route: '', moduleId: 'menu/menu', title: 'Menu'},
    ]);
    this.router = router;
  }

  constructor() {
  }
}
