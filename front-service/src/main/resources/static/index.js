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
            .when('/tasks', {
                templateUrl: 'tasks/tasks.html',
                controller: 'tasksController'
            })
            .when('/tasks/:id', {
                templateUrl: 'details/details.html',
                controller: 'detailsController'
            })
            .when('/assigned', {
                  templateUrl: 'assigned/assigned.html',
                  controller: 'assignedController'
             })
             .when('/incoming', {
                   templateUrl: 'incoming/incoming.html',
                   controller: 'incomingController'
              })
//            .when('/form', {
//                templateUrl: 'form/form.html',
//                controller: 'formController'
//            })
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

angular.module('ttsystem-front').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage, $filter) {

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

    $rootScope.isAllowed = function(viewName){
        if($rootScope.isUserLoggedIn()) {
            const permissions = $localStorage.permissions;
            const viewRules = $filter('filter')(permissions, {'view':viewName});
            return $localStorage.ttsystemUser.roles.indexOf(viewRules[0].role)!=-1;
        }
        return false;
    };
});