angular.module('ttsystem-front').controller('formController', function ($scope, $http, $location, $localStorage) {

   const contextPathCore = $localStorage.corePath;
    var alertPlaceholder = document.getElementById('Alert')


    function alert(message, type) {
      var wrapper = document.createElement('div')
      wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>'

      alertPlaceholder.append(wrapper)
    }

    $scope.send = function () {
        if (!$scope.orderDetails) {
            alert('Форма пуста..','danger');
            return;
        }
        if($scope.orderDetails.title == null || $scope.orderDetails.description == null) {

            alert('Все поля должны быть заполнены..','danger');
            return;
        }
            $http({
                url: contextPathCore + '/orders',
                method: 'POST',
                data: $scope.orderDetails
            }).then(function successCallback(response) {
                alert('Заявка принята ID: '+response.data.id +',Время регистрации : ' + response.data.date,'success');
                $scope.orderDetails = null;
            }, function errorCallback(response) {
                alert('Что-то пошло не так - попробуйте позже..','danger');
                  console.log('error');
                  console.log(response);
                  $scope.orderDetails = null;
            });
        };
});