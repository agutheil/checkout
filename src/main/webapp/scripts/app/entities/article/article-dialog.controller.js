'use strict';

angular.module('checkoutApp').controller('ArticleDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Article',
        function($scope, $stateParams, $modalInstance, entity, Article) {

        $scope.article = entity;
        $scope.load = function(id) {
            Article.get({id : id}, function(result) {
                $scope.article = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('checkoutApp:articleUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.article.id != null) {
                Article.update($scope.article, onSaveFinished);
            } else {
                Article.save($scope.article, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }
            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }
            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }
            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };

        $scope.setImage1 = function ($files, article) {
            if ($files[0]) {
                var file = $files[0];
                var fileReader = new FileReader();
                fileReader.readAsDataURL(file);
                fileReader.onload = function (e) {
                    var data = e.target.result;
                    var base64Data = data.substr(data.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        article.image1 = base64Data;
                    });
                };
            }
        };

        $scope.setImage2 = function ($files, article) {
            if ($files[0]) {
                var file = $files[0];
                var fileReader = new FileReader();
                fileReader.readAsDataURL(file);
                fileReader.onload = function (e) {
                    var data = e.target.result;
                    var base64Data = data.substr(data.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        article.image2 = base64Data;
                    });
                };
            }
        };
}]);
