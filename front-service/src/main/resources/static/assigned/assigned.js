angular.module('ttsystem-front').controller('assignedController', function ($scope, $http, $location, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadTasks = function (pageIndex) {
        $http({
                url: contextPath + 'api/v1/tasks/search',
                method: 'POST',
                data: {
                    page: pageIndex ? pageIndex : 1,
                    executorId: $localStorage.ttsystemUser ? $localStorage.ttsystemUser.userId : null,
                    from: $scope.filter ? $rootScope.addTimezoneOffset($scope.filter.from) : null,
                    to: $scope.filter ? $rootScope.addTimezoneOffset($scope.filter.to) : null,
                    status: $scope.filter ? $scope.filter.status : null
                }
        }).then(function (response) {
            $scope.AssignedTasks = response.data.content;
            $scope.currentPage = response.data.number+1;
            $scope.isFirstPage = response.data.first;
            $scope.isLastPage = response.data.last;
        });
    }

    $scope.renderDescription = function (description) {
       if(description && description.length > 200) {
            return description.slice(0, 200)+'... (Раскрыть)';
       }
       return description;
    }

    $scope.loadTasks();
});