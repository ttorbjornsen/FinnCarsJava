(function() {
    'use strict';

    angular
        .module('finnCarsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('acq-car-header', {
            parent: 'entity',
            url: '/acq-car-header',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Acq_car_headers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acq-car-header/acq-car-headers.html',
                    controller: 'Acq_car_headerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('acq-car-header-detail', {
            parent: 'entity',
            url: '/acq-car-header/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Acq_car_header'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acq-car-header/acq-car-header-detail.html',
                    controller: 'Acq_car_headerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Acq_car_header', function($stateParams, Acq_car_header) {
                    return Acq_car_header.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'acq-car-header',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('acq-car-header-detail.edit', {
            parent: 'acq-car-header-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acq-car-header/acq-car-header-dialog.html',
                    controller: 'Acq_car_headerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Acq_car_header', function(Acq_car_header) {
                            return Acq_car_header.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('acq-car-header.new', {
            parent: 'acq-car-header',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acq-car-header/acq-car-header-dialog.html',
                    controller: 'Acq_car_headerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                url: null,
                                load_date: null,
                                load_time: null,
                                location: null,
                                year: null,
                                km: null,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('acq-car-header', null, { reload: 'acq-car-header' });
                }, function() {
                    $state.go('acq-car-header');
                });
            }]
        })
        .state('acq-car-header.edit', {
            parent: 'acq-car-header',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acq-car-header/acq-car-header-dialog.html',
                    controller: 'Acq_car_headerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Acq_car_header', function(Acq_car_header) {
                            return Acq_car_header.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('acq-car-header', null, { reload: 'acq-car-header' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('acq-car-header.delete', {
            parent: 'acq-car-header',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acq-car-header/acq-car-header-delete-dialog.html',
                    controller: 'Acq_car_headerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Acq_car_header', function(Acq_car_header) {
                            return Acq_car_header.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('acq-car-header', null, { reload: 'acq-car-header' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
