angular.module('ttsystem-front').controller('assignedController', function ($scope, $http, $location, $localStorage) {
   const contextPathCore = $localStorage.corePath;

     $scope.loadOrders = function (pageIndex = 1) {

                $http({
                    url: contextPathCore + '/processes/execution',
                    method: 'GET',
                    params: {
                        p: pageIndex,
                        old_date: $scope.filter ? $scope.filter.oldDate : null,
                        new_date: $scope.filter ? $scope.filter.newDate : null
                    }
                }).then(function (response) {
                    $scope.OrdersPage = response.data;
                    $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.OrdersPage.totalPages);
                });
            };
            function alert(message, type) {
                 var alertPlaceholder = document.getElementById('alert');
                   var wrapper = document.createElement('div');
                   wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';

                   alertPlaceholder.append(wrapper);
                 }
            $scope.loadUsersInfo = function (username) {
                  $http({
                          url: contextPathCore + '/info',
                          method: 'POST' ,
                          data:{user : username}

                   }).then(function (response) {
                         $scope.currentUser = response.data;
                   });

                   };



              $scope.generatePagesIndexes = function (startPage, endPage) {
                    let arr = [];
                    for (let i = startPage; i < endPage + 1; i++) {
                          arr.push(i);
                    }
                    return arr;
                    }
              $scope.isAllowed = function(elem){
                    var result = $localStorage.allowance.roles.includes(elem);
                    return result ;

              }
              $scope.isNotAccepted = function(elem){
                     var result = "пока не принят".includes(elem);
                     return result ;

                }
              $scope.isNotFinished = function(elem){
                     var result = "процесс не завешён".includes(elem);
                      return result ;

               }
              if($scope.isAllowed('EMPLOYEE')){
                    $scope.loadOrders();
              }

 $scope.accept = function (processId) {
          $http({
                  url: contextPathCore + '/processes/accept',
                  method: 'POST' ,
                  data:{id : processId}

           }).then(function successCallback(response) {
                            alert('Вы успешно приступили к работе над заявкой. Время регистрации : ' + response.data.date,'success');
                            $scope.loadOrders();

                   }, function errorCallback(response) {
                              alert('Что-то пошло не так - попробуйте позже..' ,'danger');
                              $scope.loadOrders();


                   });

           };
 $scope.postpone = function (processId) {
           $http({
                   url: contextPathCore + '/processes/postpone',
                   method: 'POST' ,
                   data:{id : processId}

            }).then(function successCallback(response) {
                             alert('Вы успешно отложили работу над заявкой. Время регистрации : ' + response.data.date,'success');
                             $scope.loadOrders();

                    }, function errorCallback(response) {
                               alert('Что-то пошло не так - попробуйте позже..' ,'danger');
                               $scope.loadOrders();


                    });

            };
 $scope.finish = function (processId, commit) {
           $http({
                   url: contextPathCore + '/processes/finish',
                   method: 'POST' ,
                   data:{id : processId , message : commit}

            }).then(function successCallback(response) {
                             alert('Вы успешно отчитались о выполнении. Время регистрации : ' + response.data.date,'success');
                             $scope.loadOrders();

                    }, function errorCallback(response) {
                               alert('Что-то пошло не так - попробуйте позже..' ,'danger');
                               $scope.loadOrders();


                    });

            };
 $scope.tryToFinish = function(processId){
        if($scope.commit==null){
            alert('Перед закрытием обязательно надо отсавить комментарий' ,'danger');
            return;
        }
        finish(processId,$scope.commit);




 }


});