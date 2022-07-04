angular.module('ttsystem-front').controller('ordersController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadOrders = function (pageIndex) {
        $http({
                url: contextPath + 'api/v1/orders',
                method: 'GET',
                params: {
                    page: pageIndex ? pageIndex : 1,
//                    from: $scope.newFilter ? $scope.newFilter.from : null,
//                    to: $scope.newFilter ? $scope.newFilter.to : null,
                }
        }).then(function (response) {
            $scope.MyOrders = response.data.content;
            //[{id: 1, title: 'Ошибка включения компутера', description: 'Не могу включить компутер умираю уже капец блин почините', status: 'Готово', createdAt: ''},
            //{id: 2, title: 'Не работает', description: 'Не могу включить компутер умираю уже капец блин почините Не могу включить компутер умираю уже капец блин почините Не могу включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините', status: 'В работе', createdAt: '2022-10-01'}];
            console.log($scope.MyOrders);
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

    $scope.open = function (orderId) {
        $scope.showModal = true;
        $scope.currentItem = $scope.MyOrders.find(o => o.id === orderId);
        $('#item-modal').show();
    };

    $scope.cancel = function () {
        $scope.showModal = false;
        $('#item-modal').hide();
    };

    $scope.loadOrders();
});