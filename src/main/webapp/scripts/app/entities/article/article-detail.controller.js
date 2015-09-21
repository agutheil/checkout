'use strict';

angular.module('checkoutApp')
    .controller('ArticleDetailController', function ($scope, $rootScope, $stateParams, entity, Article) {
        $scope.article = entity;
        $scope.load = function (id) {
            Article.get({id: id}, function(result) {
                $scope.article = result;
            });
        };
        $rootScope.$on('checkoutApp:articleUpdate', function(event, result) {
            $scope.article = result;
        });
    });
