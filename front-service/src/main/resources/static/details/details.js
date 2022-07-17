angular.module('ttsystem-front').controller('detailsController', function ($scope, $http, $location, $route) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadTaskWithComments = function () {
        $http.get(contextPath + 'api/v1/tasks/'+$route.current.params.id)
            .then(function (response) {
                $scope.Task = response.data;
                $scope.loadComments($scope.Task.id);
            });
    }

    $scope.loadComments = function (taskId) {
        $http({
            url: contextPath + 'api/v1/comments',
            method: 'GET',
            params: {taskId: taskId}
        }).then(function (response) {
            $scope.Comments = response.data;
        });
    }

    $scope.isAuthorTaskOwner = function (authorId) {
        return authorId === $scope.Task.ownerId;
    }

    $scope.sendComment = function() {
        $scope.newComment.taskId = $route.current.params.id;
        $http({
            url: contextPath + 'api/v1/comments',
            method: 'POST',
            data: $scope.newComment
        }).then(function successCallback(response) {
            $scope.newComment = null;
            $scope.loadComments($route.current.params.id)
        }, function errorCallback(response) {
            alert('Что-то пошло не так - попробуйте позже..','danger');
              console.log('error');
              console.log(response);
              $scope.newComment = null;
        });
    }

    $scope.loadTaskWithComments();
});