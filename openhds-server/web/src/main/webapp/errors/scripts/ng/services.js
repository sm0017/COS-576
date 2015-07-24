var errorServices = angular.module('errorServices', ['ngResource']);

errorServices.factory('RecentErrorsService', ['$resource',
    function($resource){
        return $resource('/openhds/api/rest/errors', {}, {
            query: {method:'GET', params:{resolution:'Unresolved'}}
        });
    }]);

errorServices.factory('ErrorQueryService', ['$resource', function($resource) {

    return $resource("/openhds/api/rest/errors/:uuid", { uuid: '@uuid' }, {
        query: {method: 'GET', isArray: false}
    });

}]);