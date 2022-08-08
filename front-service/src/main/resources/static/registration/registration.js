angular.module('ttsystem-front').controller('registrationController', function ($scope, $rootScope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:5555/auth/api/v1';
    const corePath = 'http://localhost:5555/core/api/v1';

    var alertPlaceholder = document.getElementById('liveAlertPlaceholder')
    function alert(message, type) {
      var wrapper = document.createElement('div')
      wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>'

      alertPlaceholder.append(wrapper)
    }

    $rootScope.registration = function () {
        if($scope.dto.password == $scope.validation.password){


            $http.post(contextPath+'/registration', $scope.dto)
                 .then(function successCallback(response) {
                       $scope.validation = null;
                       $scope.dto = null
                      $location.path('/login');
                 }, function errorCallback(response) {
                      console.log(response);
                      alert("Ошибка при регистрации!",'danger');
                 });
        }else{
            alert("Пароли не совпадают",'danger');
        }

    };

    (function () {



      var forms = document.querySelectorAll('.needs-validation')


      Array.prototype.slice.call(forms)
        .forEach(function (form) {
          form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
              event.preventDefault()
              event.stopPropagation()
            }

            form.classList.add('was-validated')
          }, false)
        })
    })()
});