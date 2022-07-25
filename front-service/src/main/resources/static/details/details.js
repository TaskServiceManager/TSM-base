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

   $scope.showAssignModal = function () {
        $http({
          url: 'http://localhost:5555/auth/api/v1/users', //заменить на кор
          method: 'GET',
          params: {role: 'ROLE_EXECUTOR'}
        }).then(function successCallback(response) {
          $scope.ExecutorsList = response.data;
        }, function errorCallback(response) {
          alert('Что-то пошло не так - попробуйте позже..','danger');
          console.log('error');
          console.log(response);
        });
       $('#item-modal-assign').show();
   };

   $scope.closeAssignModal = function () {
       $scope.assignedExecutors = null;
       $scope.assignChief = null;
       $('#item-modal-assign').hide();
   };

   $scope.addExecutorForTask = function (executorId) {
       executors = $scope.assignedExecutors;
       if(executors && executors[0]) {
          const index = executors.indexOf(executorId);
          if(index==-1) {
            $scope.assignedExecutors.push(executorId);
          } else {
            $scope.assignedExecutors.splice(index, 1);
          }
       } else {
          $scope.assignedExecutors = [];
          $scope.assignedExecutors.push(executorId);
       }
   }

   $scope.addChiefForTask = function (executorId) {
       $scope.assignChief = executorId;
       if($scope.assignedExecutors.indexOf(executorId)==-1) $scope.addExecutorForTask(executorId);
   }

   $scope.assignExecutors = function () {
        console.log($scope.assignedExecutors);
        console.log($scope.assignChief);
        if(!$scope.assignedExecutors || !$scope.assignedExecutors[0] || !$scope.assignChief) {
            alert('Выберите хотя бы одного исполнителя и назначьте ответственного','danger');
            return;
        }
        $http({
            url: contextPath + 'api/v1/tasks/assign/'+$scope.Task.id,
            method: 'POST',
            data: { executorIds: $scope.assignedExecutors,
                    chiefId: $scope.assignChief
            }
        }).then(function successCallback(response) {
            $scope.closeAssignModal();
            $scope.loadTaskWithComments();
        }, function errorCallback(response) {
            alert('Что-то пошло не так - попробуйте позже..','danger');
              console.log('error');
              console.log(response);
              $scope.newComment = null;
        });
   }

    $scope.loadTaskWithComments();
});