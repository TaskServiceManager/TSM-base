angular.module('ttsystem-front').controller('editController', function ($scope, $http, $location, $localStorage, $rootScope, $route) {
    const contextPath = 'http://localhost:5555/auth/';
     var alertPlaceholder = document.getElementById('liveAlertPlaceholder')
        function alert(message, type) {
          var wrapper = document.createElement('div')
          wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>'

          alertPlaceholder.append(wrapper)
        }

    $scope.update = function (userId) {
        if($scope.dto) {
            if($scope.dto.password && !$scope.validation || $scope.dto.password && $scope.validation && $scope.dto.password != $scope.validation.password) {
                alert("Пароли не совпадают",'danger');
                return;
            }
            $scope.dto.id = userId;
            $http.put(contextPath+'api/v1/admin/users', $scope.dto)
                 .then(function successCallback(response) {
                       $scope.validation = null;
                       $scope.dto = null;
                       $scope.loadUserData();
                 }, function errorCallback(response) {
                      alert(response.data.message);
                      console.log('error');
                      console.log(response);
                 });
        }
    };


    $scope.loadUserData = function(){
        $http({
            url: contextPath + 'api/v1/users/'+$route.current.params.id+'/data',
            method: 'GET'
        }).then(function successCallback(response) {
                $scope.EditUser = response.data;
                console.log($scope.EditUser);
        }, function errorCallback(response) {
                alert(response.data.message);
                console.log('error');
                console.log(response);
        });
    }

    $scope.loadUserData();
});