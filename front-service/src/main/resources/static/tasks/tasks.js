angular.module('ttsystem-front').controller('tasksController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadTasks = function (pageIndex) {
        $http({
                url: contextPath + 'api/v1/tasks',
                method: 'GET',
                params: {
                    page: pageIndex ? pageIndex : 1,
//                    from: $scope.newFilter ? $scope.newFilter.from : null,
//                    to: $scope.newFilter ? $scope.newFilter.to : null,
                }
        }).then(function (response) {
            $scope.MyTasks = response.data.content;
            //[{id: 1, title: 'Ошибка включения компутера', description: 'Не могу включить компутер умираю уже капец блин почините', status: 'Готово', createdAt: ''},
            //{id: 2, title: 'Не работает', description: 'Не могу включить компутер умираю уже капец блин почините Не могу включить компутер умираю уже капец блин почините Не могу включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините включить компутер умираю уже капец блин почините', status: 'В работе', createdAt: '2022-10-01'}];
            console.log($scope.MyTasks);
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

    $scope.open = function (taskId) {
        $scope.showModal = true;
        $scope.currentItem = $scope.MyTasks.find(o => o.id === taskId);
        $('#item-modal').show();
    };

    $scope.cancel = function () {
        $scope.showModal = false;
        $('#item-modal').hide();
    };

    $scope.loadTasks();
});