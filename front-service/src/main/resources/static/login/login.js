angular.module('ttsystem-front').controller('loginController', function ($scope, $rootScope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:5555/auth';
    const corePath = 'http://localhost:5555/core';

    $rootScope.auth = function () {
        $rootScope.user=$scope.user;
        $http.post(contextPath+'/auth', $rootScope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.ttsystemUser = {username: $rootScope.user.username, token: response.data.token};
                    $rootScope.user.username = null;
                    $rootScope.user.password = null;
                    $http({
                       url: corePath+'/api/v1/orders/getRole',
                       method: 'GET'
                   }).then(function (response) {
                       console.log(response.data);
                       $localStorage.allowance = response.data;
                   });
                   $location.path('/');
                }
            }, function errorCallback(response) {
                alert("Неверный логин/пароль");
            });
        };
});