'use strict';

angular.module('checkoutApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
