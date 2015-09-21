'use strict';

angular.module('checkoutApp')
    .controller('OrderDetailController', function ($scope, $rootScope, $stateParams, entity, Order) {
        $scope.order = entity;
        $scope.load = function (id) {
            Order.get({id: id}, function(result) {
                $scope.order = result;
            });
        };
        $rootScope.$on('checkoutApp:orderUpdate', function(event, result) {
            $scope.order = result;
        });
    });
