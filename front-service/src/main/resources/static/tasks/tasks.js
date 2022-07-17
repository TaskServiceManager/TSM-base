angular.module('ttsystem-front').controller('tasksController', function ($scope, $http, $location, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadTasks = function (pageIndex) {
        $http({
                url: contextPath + 'api/v1/tasks',
                method: 'GET',
                params: {
                    ownerId: $localStorage.ttsystemUser ? $localStorage.ttsystemUser.userId : null,
                    page: pageIndex ? pageIndex : 1,
//                    from: $scope.newFilter ? $scope.newFilter.from : null,
//                    to: $scope.newFilter ? $scope.newFilter.to : null,
                }
        }).then(function (response) {
            $scope.MyTasks = response.data.content;
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

//    $scope.open = function (taskId) {
//        $scope.showModal = true;
//        $scope.currentItem = $scope.MyTasks.find(o => o.id === taskId);
//        $('#item-modal').show();
//    };
//
//    $scope.cancel = function () {
//        $scope.showModal = false;
//        $('#item-modal').hide();
//    };

    $scope.loadTasks();
});