angular.module('register',[]).controller("RegisterController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2'
    $scope.saveUser = function () {
        const url = contextPath + '/user/registration';
        console.log("Method saveUser(), url: " + url);
        let user_info={
            experience: 0,
            city: "Информации нет",
            position: "Информации нет",
            male: $scope.Male
        }
        $scope.NewUser.user_info=user_info;
        console.log($scope.NewUser);
        if($scope.NewUser.name==null || $scope.NewUser.email==null || $scope.NewUser.password==null || $scope.Male==null
            || $scope.NewUser.password.length<3){

            alert("Не все поля заполнены или заполнены некорректно!")
        }
        else {
            if ($scope.NewUser.password == $scope.RePass) {
                $http.post(url, $scope.NewUser)
                    .then(function (response) {
                        if(response.data){
                            window.location.replace('http://localhost:8081/logIn.html');
                            console.log("Original email!")
                        }
                        else{
                            alert("Это почта уже используется!")
                        }
                    });
                console.log("Response received")
            } else {
                alert("Пароли не совпадают!")
            }
        }
    };

});
