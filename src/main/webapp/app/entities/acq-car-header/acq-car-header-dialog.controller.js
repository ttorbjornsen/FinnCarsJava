(function() {
    'use strict';

    angular
        .module('finnCarsApp')
        .controller('Acq_car_headerDialogController', Acq_car_headerDialogController);

    Acq_car_headerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Acq_car_header'];

    function Acq_car_headerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Acq_car_header) {
        var vm = this;

        vm.acq_car_header = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.acq_car_header.id !== null) {
                Acq_car_header.update(vm.acq_car_header, onSaveSuccess, onSaveError);
            } else {
                Acq_car_header.save(vm.acq_car_header, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('finnCarsApp:acq_car_headerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
