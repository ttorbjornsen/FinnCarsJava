(function() {
    'use strict';

    angular
        .module('finnCarsApp')
        .controller('Acq_car_headerDeleteController',Acq_car_headerDeleteController);

    Acq_car_headerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Acq_car_header'];

    function Acq_car_headerDeleteController($uibModalInstance, entity, Acq_car_header) {
        var vm = this;

        vm.acq_car_header = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Acq_car_header.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
