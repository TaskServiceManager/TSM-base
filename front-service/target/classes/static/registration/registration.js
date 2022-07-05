angular.module('ttsystem-front').controller('registrationController', function ($scope, $http,$localStorage) {


     const contextPathAuth = $localStorage.authPath;
     var alertPlaceholder = document.getElementById('regAlert')


        function alert(message, type) {
          var wrapper = document.createElement('div')
          wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>'

          alertPlaceholder.append(wrapper)
        }

        $scope.registration = function () {

            $http({
                                url: contextPathAuth +'/reg',
                                method: 'POST',
                                data: $scope.regDetails
                            }).then(function successCallback(response) {
                                alert('Пользователь : '+response.data.username +' ID: '+response.data.id + ', Время регистрации : ' + response.data.date,'success');
                                $scope.regDetails = null;
                            }, function errorCallback(response) {
                                alert('Что-то пошло не так - попробуйте позже.. Статус: ' + response.data.status,'danger');
                                  console.log('error');
                                  console.log(response);
                                  $scope.regDetails = null;
                            });
                        };





        $scope.checkPass = function(){
            return $scope.regPass.password1===$scope.regPass.password2;
        }
        $scope.checkFields = function(){
            return ($scope.regDetails.firstName!=null)&&($scope.regDetails.lastName!=null)
            &&($scope.regDetails.patronymic!=null)&&($scope.regDetails.username!=null)
            &&($scope.regPass.password1!=null)&&($scope.regPass.password2!=null)&&($scope.regDetails.email!=null)
           &&($scope.regDetails.company!=null)&&($scope.regDetails.phone!=null)
           &&($scope.regDetails.building!=null)&&($scope.regDetails.office!=null);
        }
        $scope.checkForm = function(){
             if(!$scope.checkFields()){
                alert('Не все поля заполнены','danger');
                return;
             }
             if(!$scope.checkPass())   {
                    alert('Пароли не совпадают','danger');
                    return;
             }
             $scope.regDetails.password = $scope.regPass.password1;
             $scope.registration();


        }
});