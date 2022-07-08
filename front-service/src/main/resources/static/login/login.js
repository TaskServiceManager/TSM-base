angular.module('ttsystem-front').controller('loginController', function ($scope, $rootScope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:5555/auth/api/v1';
    const corePath = 'http://localhost:5555/core/api/v1';

    $rootScope.auth = function () {
        $rootScope.user=$scope.user;
        console.log($rootScope.user);
        $http.post(contextPath+'/auth', $rootScope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.ttsystemUser = {username: $rootScope.user.username, token: response.data.token};
                    $rootScope.user.username = null;
                    $rootScope.user.password = null;
                    $http({
                       url: corePath+'/api/v1/orders/roles',
                       method: 'GET'
                   }).then(function (response) {
                       console.log(response.data);
                       $localStorage.allowance = response.data;
                   });
                   $location.path('/');
                }
            }, function errorCallback(response) {
                console.log(response);
                alert("Неверный логин/пароль");
            });
        };
});