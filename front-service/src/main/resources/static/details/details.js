angular.module('ttsystem-front').controller('detailsController', function ($scope, $http, $location, $route, $localStorage, $filter) {
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
          $scope.prepareModel();
        }, function errorCallback(response) {
          alert('Что-то пошло не так - попробуйте позже..','danger');
          console.log('error');
          console.log(response);
        });
       $('#item-modal-assign').show();
   };

   $scope.prepareModel = function () {
        angular.forEach($scope.ExecutorsList, function(value, key) {
          value.isChief = $scope.Task.chief&&$scope.Task.chief.id==value.id ? true : false;
          value.isAssigned = $scope.isAlreadyAssigned(value.id);
        });
   }

   $scope.closeAssignModal = function () {
       $('#item-modal-assign').hide();
   };

   $scope.changeChiefForTask = function (executor) {
       const assignedChief = $filter('filter')($scope.ExecutorsList, {'isChief':true});
       angular.forEach(assignedChief, function(value, key) {
         value.isChief = false;
       });
       executor.isAssigned=true;
   }

   $scope.assignExecutors = function () {
        const assignedExecutorsObjects = $filter('filter')($scope.ExecutorsList, {'isAssigned':true});
        const assignedChief = $filter('filter')($scope.ExecutorsList, {'isChief':true});
        const assignedExecutors = assignedExecutorsObjects.map(function(obj) {
          return obj.id;
        });
        if(!assignedExecutors || !assignedExecutors[0] || !assignedChief || !assignedChief[0]) {
            alert('Выберите хотя бы одного исполнителя и назначьте ответственного','danger');
            return;
        }
        $http({
            url: contextPath + 'api/v1/tasks/assign/'+$scope.Task.id,
            method: 'POST',
            data: { executorIds: assignedExecutors,
                    chiefId: assignedChief[0].id
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

   $scope.isAlreadyAssigned = function(executorId) {
        const foundExecutor = $filter('filter')($scope.Task.executors, {'id':executorId});
        return foundExecutor[0]!=null;
   }

    $scope.loadTaskWithComments();
});