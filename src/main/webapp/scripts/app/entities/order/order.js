'use strict';

angular.module('checkoutApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('order', {
                parent: 'entity',
                url: '/orders',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Orders'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/order/orders.html',
                        controller: 'OrderController'
                    }
                },
                resolve: {
                }
            })
            .state('order.detail', {
                parent: 'entity',
                url: '/order/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Order'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/order/order-detail.html',
                        controller: 'OrderDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Order', function($stateParams, Order) {
                        return Order.get({id : $stateParams.id});
                    }]
                }
            })
            .state('order.new', {
                parent: 'order',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/order/order-dialog.html',
                        controller: 'OrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {coreArticleId: null, transationId: null, paymentStatus: null, email: null, payerId: null, payerStatus: null, firstName: null, lastName: null, shipToName: null, shipToStreet: null, shipToCity: null, shipToState: null, shipToCntryCode: null, shipToZip: null, addressStatus: null, totalAmt: null, currencyCode: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('order', null, { reload: true });
                    }, function() {
                        $state.go('order');
                    })
                }]
            })
            .state('order.edit', {
                parent: 'order',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/order/order-dialog.html',
                        controller: 'OrderDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Order', function(Order) {
                                return Order.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('order', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
