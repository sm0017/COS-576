'use strict';

/* App Module */

var angularFront = angular.module('angularFront ',
    ['ngRoute',
     'locControllers',
     'locServices']
    );

angularFront.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/locations', {
        templateUrl: '/partials/index.html',
        controller: 'listCtrl'
      }).
      when('/locations/:extId', {
        templateUrl: '/partials/location-details.html',
        controller: 'locDetailsCtrl'
      }).
        otherwise({
        redirectTo: '/locations'
      });
  }]);
