'use strict';

angular.module('checkoutApp')
    .controller('OrderController', function ($scope, Order) {
        $scope.orders = [];
        $scope.loadAll = function() {
            Order.query(function(result) {
               $scope.orders = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Order.get({id: id}, function(result) {
                $scope.order = result;
                $('#deleteOrderConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Order.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.order = {coreArticleId: null, transactionId: null, paymentStatus: null, email: null, payerId: null, payerStatus: null, firstName: null, lastName: null, shipToName: null, shipToStreet: null, shipToCity: null, shipToState: null, shipToCntryCode: null, shipToZip: null, addressStatus: null, totalAmt: null, currencyCode: null, id: null};
        };
    });
