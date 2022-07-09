angular.module('ttsystem-front').controller('loginController', function ($scope, $rootScope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:5555/auth/api/v1';
    const corePath = 'http://localhost:5555/core/api/v1';

    $rootScope.auth = function () {
        $rootScope.user=$scope.user;
        $http.post(contextPath+'/auth', $rootScope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    const jwt = response.data.token;
                    const payload = JSON.parse(atob(jwt.split('.')[1]));

                    $localStorage.ttsystemUser = {username: $rootScope.user.username,
                                                userId: payload.id,
                                                roles: payload.role,
                                                token: jwt};

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