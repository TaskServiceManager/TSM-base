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
                url: contextPath + 'api/v1/admin/users',
                method: 'GET',
                params: {

                    page: pageIndex ? pageIndex : 1,
                    id: $scope.filter ? $scope.filter.id : null,
                    usernamePart: $scope.filter ? $scope.filter.usernamePart : null,
                    roleId: $scope.filter ? $scope.filter.roleId : null,

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
                        method: 'GET'

                }).then(function successCallback(response) {
                                            $rootScope.CurrentUser = response.data;
                                            $location.path('/edit');

                                    }, function errorCallback(response) {
                                            console.log(response);
                                            alert("Ошибка редактирования!",'danger');
                                    });
    }

    $scope.changeRole = function(userId,newRole){
           $http({

                          url: contextPath + 'api/v1/admin' + '/users/'+ userId + '/role/',
                          method: 'PATCH',
                          params: {
                               newRole: newRole
                         }
                  }).then(function successCallback(response) {

                          $location.path('/users');
                          alert("Роль назначена!",'success');
                  }, function errorCallback(response) {
                          console.log(response);
                          alert("Ошибка при смене роли!",'danger');
                  });
    }


    $scope.loadUsers();
});