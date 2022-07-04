(function () {
    angular
        .module('ttsystem-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/form', {
                templateUrl: 'form/form.html',
                controller: 'formController'
            })
            .when('/registration', {
                            templateUrl: 'registration/registration.html',
                            controller: 'registrationController'
            })

            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .when('/assigned', {
                templateUrl: 'assigned/assigned.html',
                controller: 'assignedController'
            })
            .when('/admin', {
                templateUrl: 'admin/admin.html',
                controller: 'adminController'
            })
            .when('/income', {
                 templateUrl: 'income/income.html',
                 controller: 'incomeController'
            })
            .when('/personal', {
                  templateUrl: 'personal/personal.html',
                  controller: 'personalController'
             })

            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.ttsystemUser) {
            try {
                let jwt = $localStorage.ttsystemUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.ttsystemUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            if ($localStorage.ttsystemUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.ttsystemUser.token;
            }
        }

    }
})();

angular.module('ttsystem-front').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {
 const oldIp ='10.123.3.228';
 const currentIp = 'localhost';
 const contextPathCore = 'http://'+currentIp+':5555/core/api/v1';
 const contextPathAuth = 'http://'+currentIp+':5555/auth/api/v1';
 $localStorage.corePath = contextPathCore;
 $localStorage.authPath = contextPathAuth;

    $scope.tryToAuth = function () {
        $http.post( contextPathAuth + '/auth', $scope.user)
            .then(function successCallback(response) {
                console.log(response.data);
                if (response.data.token) {

                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;


                   $localStorage.ttsystemUser = {username: $scope.user.username, token: response.data.token}



                    $scope.user.username = null;
                    $scope.user.password = null;
                    $http({
                                url: contextPathCore + '/orders/roles',
                                method: 'GET'
                            }).then(function (response) {

                                $localStorage.allowance = response.data;


                            });



                    $location.path('/');
                }
            }, function errorCallback(response) {

                console.log('login error');
                console.log (response);
                $rootScope.tryToLogout();
            });

    };


    $rootScope.tryToLogout = function () {

        $scope.clearUser();

        $scope.user = null;


        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.allowance;
        delete $localStorage.ttsystemUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.ttsystemUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.goToRegistration = function(){
         $location.path('/registration');
    }
});