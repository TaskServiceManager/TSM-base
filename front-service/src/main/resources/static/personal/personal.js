angular.module('ttsystem-front').controller('personalController', function ($scope, $http, $location, $localStorage) {

    const contextPathCore = $localStorage.corePath;


        $scope.loadPersonalInfo = function () {

                    $http({
                        url: contextPathCore + '/personal',
                        method: 'GET'

                    }).then(function (response) {
                        $scope.UserDto = response.data;
                    });
                };
        $scope.isAllowed = function(elem){
              var result = $localStorage.allowance.roles.includes(elem);
              return result ;

        }





    $scope.loadPersonalInfo();




});