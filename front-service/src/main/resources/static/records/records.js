angular.module('ttsystem-front').controller('recordsController', function ($scope, $http, $location, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadRecords = function (pageIndex) {
        $http({
                url: contextPath + 'api/v1/time/report',
                method: 'GET',
                params: {

                    page: pageIndex ? pageIndex : 1,
                    from: $scope.filter ? $rootScope.addTimezoneOffset($scope.filter.from) : null,
                    to: $scope.filter ? $rootScope.addTimezoneOffset($scope.filter.to) : null,

                }
        }).then(function (response) {
            $scope.MyRecords = response.data.content;
            $scope.currentPage = response.data.number+1;
            $scope.isFirstPage = response.data.first;
            $scope.isLastPage = response.data.last;
        });
    }


    $scope.loadRecords();
});