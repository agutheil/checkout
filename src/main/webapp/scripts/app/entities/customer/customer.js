'use strict';

<<<<<<< HEAD
angular.module('schubberApp')
=======
angular.module('mightymerceApp')
>>>>>>> customer und adresse
    .config(function ($stateProvider) {
        $stateProvider
            .state('customer', {
                parent: 'entity',
                url: '/customer',
                data: {
<<<<<<< HEAD
                    roles: ['ROLE_USER']
=======
                    roles: ['ROLE_USER'],
                    pageTitle: 'mightymerceApp.customer.home.title'
>>>>>>> customer und adresse
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customer/customers.html',
                        controller: 'CustomerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('customer');
                        return $translate.refresh();
                    }]
                }
            })
            .state('customerDetail', {
                parent: 'entity',
                url: '/customer/:id',
                data: {
<<<<<<< HEAD
                    roles: ['ROLE_USER']
=======
                    roles: ['ROLE_USER'],
                    pageTitle: 'mightymerceApp.customer.detail.title'
>>>>>>> customer und adresse
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customer/customer-detail.html',
                        controller: 'CustomerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('customer');
                        return $translate.refresh();
                    }]
                }
            });
    });
