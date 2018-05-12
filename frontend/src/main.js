import environment from "./environment";
import {I18N, TCustomAttribute} from 'aurelia-i18n';
import Backend from 'i18next-xhr-backend';

export function configure(aurelia) {
  aurelia.use
    .standardConfiguration()
    .feature('resources')
    .plugin('aurelia-i18n', (instance) => {
      let aliases = ['t', 'i18n'];
      TCustomAttribute.configureAliases(aliases);
      instance.i18next.use(Backend);
      return instance.setup({
        backend: {
          loadPath: 'src/locales/{{lng}}/{{ns}}.json',
        },
        attributes: aliases,
        lng : 'et',
        fallbackLng : 'en',
        skipTranslationOnMissingKey: true,
        debug : true
      });
    });

  if (environment.debug) {
    aurelia.use.developmentLogging();
  }

  if (environment.testing) {
    aurelia.use.plugin('aurelia-testing');
  }

  aurelia.start().then(() => aurelia.setRoot());
}
