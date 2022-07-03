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
            .when('/login', {
                templateUrl: 'login/login.html',
                controller: 'loginController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
//            .when('/form', {
//                templateUrl: 'form/form.html',
//                controller: 'formController'
//            })
//
//            .when('/details', {
//                templateUrl: 'details/details.html',
//                controller: 'detailsController'
//            })
//            .when('/personal', {
//                  templateUrl: 'personal/personal.html',
//                  controller: 'personalController'
//             })
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

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.ttsystemUser) {
            return true;
        } else {
            return false;
        }
    };

    $rootScope.redirectCheck = function () {
        if(!$rootScope.isUserLoggedIn()) {
            $rootScope.wayForAuth = $location.url();
            $location.path('/login');
        }
    };

    $rootScope.redirectCheck();

    $rootScope.logout = function () {
        $rootScope.clearUser();
        $rootScope.clearRole();
        $rootScope.user = null;
        $rootScope.allowance = null;
        $location.path('/');
    };

    $rootScope.clearUser = function () {
        delete $localStorage.ttsystemUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.clearRole = function () {
        delete $localStorage.allowance;
    };
//
//    $rootScope.isAllowed = function(elem){
//        console.log(elem);
//       return $localStorage.allowance.indexOf(elem) != -1;
//    };
});