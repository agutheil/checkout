'use strict';

<<<<<<< HEAD
angular.module('schubberApp')
=======
angular.module('mightymerceApp')
>>>>>>> customer und adresse
    .factory('Customer', function ($resource) {
        return $resource('api/customers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
<<<<<<< HEAD
            }
=======
            },
            'update': { method:'PUT' }
>>>>>>> customer und adresse
        });
    });
