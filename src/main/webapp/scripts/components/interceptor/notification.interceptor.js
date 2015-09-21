 'use strict';

angular.module('checkoutApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-checkoutApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-checkoutApp-params')});
                }
                return response;
            },
        };
    });