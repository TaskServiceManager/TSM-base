angular.module('ttsystem-front').controller('statisticsController', function ($scope, $http, $location, $localStorage) {


$scope.isAllowed = function(elem){
              var result = $localStorage.allowance.roles.includes(elem);

              return result ;

        }
});