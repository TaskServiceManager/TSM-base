angular.module('ttsystem-front').controller('usersController', function ($scope, $http, $location, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:5555/auth/';

    $scope.loadUsers = function (pageIndex) {
        $http({
                url: contextPath + 'api/v1/admin/users',
                method: 'GET',
                params: {
                    page: pageIndex ? pageIndex : 1,
                    id: $scope.filter ? $scope.filter.id : null,
                    usernamePart: $scope.filter ? $scope.filter.usernamePart : null,
                    roleName: $scope.filter && $scope.filter.roleName && $scope.filter.roleName!='' ? $scope.filter.roleName : null,
                }
        }).then(function (response) {
            $scope.Users = response.data.content;
            $scope.currentPage = response.data.number+1;
            $scope.isFirstPage = response.data.first;
            $scope.isLastPage = response.data.last;
        });
    }

    $scope.goToEdit = function (userId){
        $location.path('/users/'+userId+'/edit');
    }

    $scope.changeRoles = function(){
       $http({
              url: contextPath + 'api/v1/admin' + '/users/'+ $scope.modalUser.id  + '/roles/',
              method: 'PATCH',
              data: $scope.modalRoles
      }).then(function successCallback(response) {
              $location.path('/users');
              $scope.closeChangeRoleModal();
      }, function errorCallback(response) {
              alert(response.data.message);
              console.log('error');
              console.log(response);
      });
    }

    $scope.addToModalRoles = function(roleName) {
        if($scope.isRolePresent(roleName)) {
            var index = $scope.modalRoles.indexOf(roleName);
            $scope.modalRoles.splice(index, 1);
        } else {
            $scope.modalRoles.push(roleName);
        }
    }

    $scope.renderRoles = function(user) {
        return user && user.roles ? user.roles.join(', ') : '-';
    }

    $scope.showChangeRoleModal = function (user) {
       $scope.modalRoles = user.roles;
       $scope.modalUser = user;
       $('#item-modal-change-role').show();
    };

    $scope.isRolePresent = function(roleName) {
         return $scope.modalRoles ? $scope.modalRoles.indexOf(roleName)!=-1 : false;
    }

    $scope.closeChangeRoleModal = function () {
        $('#item-modal-change-role').hide();
        $scope.modalRoles = null;
        $scope.modalUser = null;
    };

    $scope.loadUsers();
});