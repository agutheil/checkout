'use strict';

angular.module('checkoutApp')
    .controller('ConfigurationController', function ($scope, ConfigurationService) {
        ConfigurationService.get().then(function(configuration) {
            $scope.configuration = configuration;
        });
    });
