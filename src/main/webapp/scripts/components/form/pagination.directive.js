/* globals $ */
'use strict';

angular.module('checkoutApp')
    .directive('checkoutAppPagination', function() {
        return {
            templateUrl: 'scripts/components/form/pagination.html'
        };
    });
