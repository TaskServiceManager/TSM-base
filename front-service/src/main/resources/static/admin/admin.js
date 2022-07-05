angular.module('ttsystem-front').controller('adminController', function ($scope, $http, $location, $localStorage) {
 const contextPathCore = $localStorage.corePath;
$scope.isAllowed = function(elem){
              var result = $localStorage.allowance.roles.includes(elem);
              return result ;

        }

});