angular.module('ttsystem-front').controller('loginController', function ($scope, $rootScope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:5555/auth/api/v1';
    const corePath = 'http://localhost:5555/core/api/v1';
    $scope.toRegistration = function () {
        $location.path('/registration');
    }
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

                    $localStorage.permissions = [{view: 'myTasks', roles: ['ROLE_USER']},
                                                {view: 'records', roles: ['ROLE_EXECUTOR']},
                                                {view: 'incoming', roles: ['ROLE_MANAGER','ROLE_EXECUTOR']},
                                                {view: 'pending', roles: ['ROLE_MANAGER']},
                                                {view: 'assigned', roles: ['ROLE_EXECUTOR']},
                                                {view: 'assignToMeButton', roles: ['ROLE_EXECUTOR']},
                                                {view: 'assignButton', roles: ['ROLE_MANAGER']},
                                                {view: 'timepointButton', roles: ['ROLE_EXECUTOR']},
                                                {view: 'approvedButton', roles: ['ROLE_EXECUTOR']},
                                                {view: 'completedButton', roles: ['ROLE_MANAGER']},
                                                {view: 'returnToAcceptedButton', roles: ['ROLE_MANAGER']},
                                                {view: 'taskSearch', roles: ['ROLE_USER','ROLE_MANAGER','ROLE_EXECUTOR']},
                                                {view: 'createTask', roles: ['ROLE_USER','ROLE_MANAGER','ROLE_EXECUTOR']},
                                                {view: 'users', roles: ['ROLE_ADMIN']}];
                    $location.path('/');

                    $rootScope.loadDetailsOpen();
                    $rootScope.loadFullUserData();
                    $rootScope.loadCurrentUserTimepoint();
                }
            }, function errorCallback(response) {
                console.log(response);
                alert("Неверный логин/пароль");
            });
        };
});