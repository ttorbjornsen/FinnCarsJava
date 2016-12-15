(function() {
    'use strict';

    angular
        .module('finnCarsApp')
        .controller('Acq_car_headerDetailController', Acq_car_headerDetailController);

    Acq_car_headerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Acq_car_header'];

    function Acq_car_headerDetailController($scope, $rootScope, $stateParams, previousState, entity, Acq_car_header) {
        var vm = this;

        vm.acq_car_header = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('finnCarsApp:acq_car_headerUpdate', function(event, result) {
            vm.acq_car_header = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
