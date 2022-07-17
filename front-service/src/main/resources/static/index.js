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

angular.module('ttsystem-front').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage, $filter, $route) {

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
        delete $localStorage.permissions;
        delete $localStorage.detailsOpen;
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
            let isAllowed = false;
            for (var i=0; i<viewRules[0].roles.length; i++) {
                if($localStorage.ttsystemUser.roles.indexOf(viewRules[0].roles[i])!=-1) isAllowed = true;
            }
            return isAllowed;
        }
        return false;
    };

    $rootScope.goToDetails = function (taskId) {
        if(!$localStorage.detailsOpen) {
            $localStorage.detailsOpen=[];
        }
        if($localStorage.detailsOpen.indexOf(taskId)==-1) {
            $localStorage.detailsOpen.push(taskId);
        }
        $rootScope.loadDetailsOpen();
        $rootScope.preDetailsView = $location.url();
        $location.path('/tasks/'+taskId);
    };

    $rootScope.loadDetailsOpen = function () {
        $rootScope.Details = $localStorage.detailsOpen;
    };

    $rootScope.deleteFromDetailsOpen = function (taskId) {
        const index = $localStorage.detailsOpen.indexOf(taskId);
        if(index!=-1) {
            $localStorage.detailsOpen.splice(index, 1);
        }
        $rootScope.loadDetailsOpen();
        if($route.current.params.id==taskId) {
            $location.path($rootScope.preDetailsView);
        }
    };

    $rootScope.renderExecutors = function (executors) {
       if(executors && executors[0]) {
            executorsShort=[];
            for (var i=0; i<executors.length; i++) {
                current = executors[i];
                if(current.lastName) {
                    executorsShort.push(current.lastName + (current.firstName ? ' ' + current.firstName.slice(0,1) : '') + (current.middleName ? '. ' + current.middleName.slice(0,1) : ''));
                }
            }
            if(executorsShort[0]) {
                return executorsShort.join(', ');
            }
       }
       return 'Не назначены';
    }

    $rootScope.loadDetailsOpen();
});