angular.module('ttsystem-front').controller('incomingController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadTasks = function (pageIndex) {
        $http({
                url: contextPath + 'api/v1/tasks/incoming',
                method: 'GET',
                params: {
                    page: pageIndex ? pageIndex : 1,
//                    from: $scope.newFilter ? $scope.newFilter.from : null,
//                    to: $scope.newFilter ? $scope.newFilter.to : null,
                }
        }).then(function (response) {
            $scope.IncomingTasks = response.data.content;
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