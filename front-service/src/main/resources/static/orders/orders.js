angular.module('ttsystem-front').controller('ordersController', function ($scope, $http, $location,$localStorage) {
    const contextPathCore = $localStorage.corePath;


    $scope.loadOrders = function (pageIndex = 1) {
            $http({
                url: contextPathCore + '/orders',
                method: 'GET',
                params: {
                    page: pageIndex,
                    from: $scope.filter ? $scope.filter.oldDate : null,
                    to: $scope.filter ? $scope.filter.newDate : null
                }
            }).then(function (response) {
                $scope.OrdersPage = response.data;
                $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.OrdersPage.totalPages);
            });
        };

        $scope.generatePagesIndexes = function (startPage, endPage) {
            let arr = [];
                for (let i = startPage; i < endPage + 1; i++) {
                    arr.push(i);
                }
            return arr;
        }
        $scope.clearForm = function(){
            $scope.filter.oldDate = null;
            $scope.filter.newDate = null;

        }

    $scope.getDescription = function(description, commits){
        $scope.myText = description;
        $scope.myCommits = commits;
    }


    $scope.loadOrders();
});