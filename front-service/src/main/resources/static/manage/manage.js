angular.module('ttsystem-front').controller('manageController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders/manager')
            .then(function (response) {
                $scope.Orders = response.data;
                //[{id: 1, title: 'Ошибка включения компутера', description: 'Не могу включить компутер умираю уже капец блин почините', status: 'Готово', createdAt: ''},
                //{id: 2, title: 'Не работает', description: 'Не могу включить компутер умираю уже капец блин почините Не могу включить компутер умираю уже капец блин почините Не могу включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините', status: 'В работе', createdAt: '2022-10-01'}];
                console.log($scope.Orders);
            });
    }

    $scope.renderDescription = function (description) {
       if(description && description.length > 200) {
            return description.slice(0, 200)+'... (Раскрыть)';
       }
       return description;
    }



    $scope.loadOrders();
});