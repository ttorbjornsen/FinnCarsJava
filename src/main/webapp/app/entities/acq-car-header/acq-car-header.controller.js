(function() {
    'use strict';

    angular
        .module('finnCarsApp')
        .controller('Acq_car_headerController', Acq_car_headerController);

    Acq_car_headerController.$inject = ['$scope', '$state', 'Acq_car_header'];

    function Acq_car_headerController ($scope, $state, Acq_car_header) {
        var vm = this;

        vm.acq_car_headers = [];

        loadAll();

        function loadAll() {
            Acq_car_header.query(function(result) {
                vm.acq_car_headers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
