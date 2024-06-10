localStorage.clear();

angular.module('logIn',[]).controller("LogInController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2'
    $scope.LogIn = function () {
        const url = contextPath + '/user/logIn';
        console.log("Method login(), url: " + url);
        console.log($scope.User.email +"\n"+$scope.User.password);
        console.log(typeof $scope.User.email);
        $http({
            url: url,
            method: 'GET',
            params:{
                email: $scope.User.email,
                password: $scope.User.password
            }
        }).then(function (response){
            console.log(response.data);
            if (response.data==""){
                alert("Неверная почта или пароль!");
            }
            else {
                // if ($scope.User.password == response.data.password) {
                    if(response.data.status.name=="block"){
                        alert("Вы заблокированы")
                    }
                    else {
                        window.location.replace('http://localhost:8081/profile.html');
                        localStorage.setItem("_user", JSON.stringify(response.data));
                    }
                // } else {
                //     alert("Неверный пароль!")
                // }
            }
        });
    };
});