angular.module('ttsystem-front').controller('editController', function ($scope, $http, $location, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:5555/auth/';
     var alertPlaceholder = document.getElementById('liveAlertPlaceholder')
        function alert(message, type) {
          var wrapper = document.createElement('div')
          wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>'

          alertPlaceholder.append(wrapper)
        }


    $rootScope.update = function (userId) {
            if($scope.dto.password == $scope.validation.password){


                $http.put(contextPath+'api/v1/admin'+'/users/'+userId, $scope.dto)
                     .then(function successCallback(response) {
                           $scope.validation = null;
                           $scope.dto = null;
                           $rootScope.CurrentUser = null;
                          $location.path('/users');
                     }, function errorCallback(response) {
                          console.log(response);
                          alert("Ошибка при редактировании!",'danger');
                     });
            }else{
                alert("Пароли не совпадают",'danger');
            }

        };


    $scope.start = function(){
        $scope.CurrentUser = $rootScope.CurrentUser;
    }
    $scope.start();

});