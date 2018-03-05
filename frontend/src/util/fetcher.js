// Fetcher is a singleton for Aurelia's Fetch client

import {HttpClient} from 'aurelia-fetch-client';

export var Fetcher = (function () {
  let instance;
  
  function createInstance() {
    let object = new HttpClient();

    // TODO: Read base url from config.json file
    object.configure(config => {
      config
        .withBaseUrl("http://localhost:8080/")
        .withDefaults({
          headers: {
            'Accept': 'application/json',
            'X-Requested-With': 'Fetch'
          }
        })
        .withInterceptor({
          request(request) {
            return request;
          },
          requestError(requestError) {
            return requestError;
          },
          response(response) {
            return response;
          },
          responseError(responseError) {
            return responseError;
          }
        });
    });

    return object;
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
