angular.module('ttsystem-front').controller('detailsController', function ($scope, $http, $location, $route) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadTask = function () {
        $http.get(contextPath + 'api/v1/tasks/'+$route.current.params.id)
            .then(function (response) {
                $scope.Task = response.data;
            });
    }

    $scope.loadTask();
});