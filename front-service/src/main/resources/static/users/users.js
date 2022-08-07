angular.module('ttsystem-front').controller('usersController', function ($scope, $http, $location, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:5555/auth/';
     var alertPlaceholder = document.getElementById('liveAlertPlaceholder')
        function alert(message, type) {
          var wrapper = document.createElement('div')
          wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>'

          alertPlaceholder.append(wrapper)
        }

    $scope.loadUsers = function (pageIndex) {
        $http({
                url: contextPath + 'api/v1/admin',
                method: 'GET',
                params: {

                    page: pageIndex ? pageIndex : 1,
                    id: $scope.filter ? $scope.filter.id : null,
                    usernamePart: $scope.filter ? $scope.filter.usernamePart : null,
                    roleId: $scope.filter ? $scope.filter.role : null,

                }
        }).then(function (response) {
            $scope.Users = response.data.content;
            $scope.currentPage = response.data.number+1;
            $scope.isFirstPage = response.data.first;
            $scope.isLastPage = response.data.last;
        });
    }
    $scope.edit = function (userId){
        $http({
                        url: contextPath + 'api/v1/users/'+userId+'/data',
                        method: 'GET',

                }).then(function (response) {
                    $scope.CurrentUser = response.data;

                });
    }
    $rootScope.update = function (userId) {
            if($scope.dto.password == $scope.validation.password){


                $http.put(contextPath+'api/v1/admin'+'/edit/user/'+userId, $scope.dto)
                     .then(function successCallback(response) {
                           $scope.validation = null;
                           $scope.dto = null
                          $location.path('/users');
                     }, function errorCallback(response) {
                          console.log(response);
                          alert("Ошибка при редактировании!",'danger');
                     });
            }else{
                alert("Пароли не совпадают",'danger');
            }

        };
    $scope.changeRole= function(userId,roleId){
           $http({
                          url: contextPath + 'api/v1/admin' + '/change/role/user/'+ userId,
                          method: 'PATCH',
                          params: {
                               roleId: roleId
                         }
                  }).then(function successCallback(response) {

                          $location.path('/users');
                  }, function errorCallback(response) {
                          console.log(response);
                          alert("Ошибка при смене роли!",'danger');
                  });
    }


    $scope.loadUsers();
});