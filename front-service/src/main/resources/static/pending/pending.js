angular.module('ttsystem-front').controller('pendingController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadTasks = function (pageIndex) {
        $http({
                url: contextPath + 'api/v1/tasks',
                method: 'GET',
                params: {
                    page: pageIndex ? pageIndex : 1,
                    status: 'APPROVED'
                }
        }).then(function (response) {
            $scope.PendingTasks = response.data.content;
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