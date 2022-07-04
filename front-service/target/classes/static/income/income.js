angular.module('ttsystem-front').controller('incomeController', function ($scope, $http, $location, $localStorage) {
   const contextPathCore = $localStorage.corePath;

 $scope.loadOrders = function (pageIndex = 1) {
            $http({
                url: contextPathCore + '/orders/management',
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
 $scope.loadEmployers = function(executors){
        $http({
                        url: contextPathCore + '/employers',
                        method: 'GET'

                    }).then(function (response) {

                        $scope.Employers = response.data;




                    });
 }



     function alert(message, type) {
     var alertPlaceholder = document.getElementById('AlertInAssign');
       var wrapper = document.createElement('div');
       wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';

       alertPlaceholder.append(wrapper);
     }

     $scope.assign = function (u,id) {


             $http({
                 url: contextPathCore + '/orders/assign',
                 method: 'POST',
                 data:  {username : u.username , orderId : id}

             }).then(function successCallback(response) {
                 alert('Исполнитель '+ response.data.executor +',Время регистрации : ' + response.data.date,'success');
                 $scope.loadOrders();

             }, function errorCallback(response) {
                 alert('Что-то пошло не так - попробуйте позже..' +response.data,'danger');
                   $scope.loadOrders();

             });
         };
         $scope.assignMe = function (orderId) {

                      $http({
                          url: contextPathCore + '/orders/assign',
                          method: 'GET',
                          params: {
                                id : orderId
                          }
                      }).then(function successCallback(response) {
                          alert('Заявка ID: '+response.data.id+' назначена. Время : ' + response.data.date,'success');
                         $scope.loadOrders();

                      }, function errorCallback(response) {
                          alert('Что-то пошло не так - попробуйте позже..' +response.data,'danger');
                          $scope.loadOrders();
                      });
                  };
        $scope.cancelOrder = function (id) {

                       $http({
                           url: contextPathCore + '/orders/cancel',
                           method: 'POST',
                           data: id

                       }).then(function successCallback(response) {
                           alert('Вы успешно отклонили заявку. Время регистрации : ' + response.data.date,'success');
                           $scope.loadOrders();

                       }, function errorCallback(response) {
                           alert('Что-то пошло не так - попробуйте позже..' +response.data,'danger');
                           $scope.loadOrders();


                       });
                   };
 $scope.info = function(executors, id){
 $http({
                 url: contextPathCore + '/processes',
                 method: 'POST',
                 data: {username : executors, orderId : id}

             }).then(function (response) {
                   console.log(response.data);
                $scope.processes = response.data;
             });
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
          if($scope.isAllowed('EMPLOYEE')){
                $scope.loadOrders();
          }
          if($scope.isAllowed('MANAGER')){
                $scope.loadEmployers();
          }
          $scope.isCreated = function(elem){
                          var result = elem.includes('CREATED');
                          return result ;

                    }
           $scope.isCompleted = function(elem){
                      var result = elem.includes('COMPLETED');
                      return result ;

           }
           $scope.isAssigned = function(executors,user,id){

                                     if(!executors.includes(user.username)) $scope.assign(user,id);
                                     else {
                                        alert('исполнитель уже назначен','danger');
                                        return;
                                     }

                               }

});