'use strict';

/* Services */

/*---ngResource consumes the REST web services */
var locServices = angular.module('locServices', ['ngResource']);

// get all locations
locServices.factory('Locations', [ '$resource',
  function($resource){

    return $resource('/api/rest/locations.json', {}, {
      query: {
                method:'GET'
             }
    });
  }]);

