(function() {
    'use strict';
    angular
        .module('finnCarsApp')
        .factory('Acq_car_header', Acq_car_header);

    Acq_car_header.$inject = ['$resource'];

    function Acq_car_header ($resource) {
        var resourceUrl =  'api/acq-car-headers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
