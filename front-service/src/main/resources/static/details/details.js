angular.module('ttsystem-front').controller('detailsController', function ($scope, $http, $location, $route, $localStorage) {
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
        return authorId === $scope.Task.owner.id;
    }

    $scope.isCurrentUserTaskOwner = function () {
        return ($localStorage.ttsystemUser ? $localStorage.ttsystemUser.userId : null) == $scope.Task.owner.id;
    }

    $scope.sendComment = function() {
        $scope.newComment.taskId = $scope.Task.id;
        $http({
            url: contextPath + 'api/v1/comments',
            method: 'POST',
            data: $scope.newComment
        }).then(function successCallback(response) {
            $scope.newComment = null;
            $scope.loadComments($scope.Task.id)
        }, function errorCallback(response) {
            alert('Что-то пошло не так - попробуйте позже..','danger');
              console.log('error');
              console.log(response);
              $scope.newComment = null;
        });
    }

    $scope.assignTaskToMe = function () {
       $http({
           url: contextPath + 'api/v1/tasks/assign/'+$scope.Task.id,
           method: 'GET'
       }).then(function successCallback(response) {
           $scope.loadTaskWithComments();
       }, function errorCallback(response) {
           alert('Что-то пошло не так - попробуйте позже..','danger');
           console.log('error');
           console.log(response);
       });
   }

   $scope.isTaskNotAssigned = function () {
       return $scope.Task && $scope.Task.executors && $scope.Task.executors.length==0 || false;
   }

    $scope.loadTaskWithComments();
});