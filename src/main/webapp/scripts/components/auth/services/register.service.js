'use strict';

angular.module('checkoutApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


