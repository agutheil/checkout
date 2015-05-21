'use strict';

angular.module('mightymerceApp')
    .factory('LineItem', function ($resource) {
        return $resource('api/lineItems/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });