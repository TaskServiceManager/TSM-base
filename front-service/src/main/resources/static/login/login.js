angular.module('ttsystem-front').controller('loginController', function ($scope, $rootScope, $http, $localStorage, $location, jwtHelper) {
    const contextPath = 'http://localhost:5555/auth/api/v1';
    const corePath = 'http://localhost:5555/core/api/v1';

    $rootScope.auth = function () {
        $rootScope.user=$scope.user;
        $http.post(contextPath+'/auth', $rootScope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    tokenValue = jwtHelper.decodeToken((response.data.token));
                    $localStorage.ttsystemUser = {username: $rootScope.user.username,
                                                userId: tokenValue.id,
                                                roles: tokenValue.role,
                                                token: response.data.token};
                    $rootScope.user.username = null;
                    $rootScope.user.password = null;
                    $location.path('/');
                }
            }, function errorCallback(response) {
                console.log(response);
                alert("Неверный логин/пароль");
            });
        };
});