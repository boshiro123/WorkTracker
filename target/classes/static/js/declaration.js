angular.module('declaration',[]).controller("DeclarationController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2';

    $scope.getUser=function () {
        $scope.User = JSON.parse(localStorage.getItem("_user"));
        if($scope.User==null){
            // window.location.replace('http://localhost:8081/logIn.html');
        }
        $scope.text = JSON.parse(localStorage.getItem("_declaration"));
        console.log(typeof $scope.User);
        console.log($scope.User);
        if ($scope.User.status.name == "common") {
            $scope.TypeTests="tests.html";
            $scope.TypeMain = "main.html"
        } else if ($scope.User.status.name == "admin") {
            $scope.TypeTests="AdminTests.html";
            $scope.TypeMain = "AdminMain.html"
            document.getElementById("WorkResults").style.display='none';

        }
    };

    $scope.sendDeclaration = function (){
        const url = contextPath + '/declaration/new';
        console.log($scope.date)
        console.log($scope.User.name)
        console.log( $scope.text)
        $http({
            url: url,
            method: 'POST',
            params:{
                text: $scope.text,
                id: $scope.User.id,
                fio: $scope.User.name,
                date: $scope.date
            }
        }).then(function (response){
        });
    }



    $scope.getUser()
});
