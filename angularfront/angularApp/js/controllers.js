'use strict';

/* Controllers */

var locControllers = angular.module('locControllers', []);

locControllers.controller('listCtrl', ['$scope', 'Locations',
  function($scope, Locations)
  {
    $scope.locations = Locations.query();
  } ]);

locControllers.controller('locDetailsCtrl',
                                       ['$scope', '$routeParams', 'Locations',
  function($scope, $routeParams,Locations)
  {
      $scope.result = Locations.get({extId: $routeParams.extId});

  }
]);


