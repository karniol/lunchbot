// Fetcher is a singleton for Aurelia's Fetch client

import {HttpClient} from "aurelia-fetch-client";
import environment from "../environment";

export var Fetcher = (function () {
  let instance;

  function createInstance() {
    let object = new HttpClient();

    object.configure(config => {
      config
        .withBaseUrl(environment.serverBaseUrl)
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
