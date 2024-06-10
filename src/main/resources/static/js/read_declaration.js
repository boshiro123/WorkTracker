angular.module('read_declaration',[]).controller("DeclarationController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2';

    $scope.getUser=function () {
        $scope.User = JSON.parse(localStorage.getItem("_user"));
        if($scope.User==null){
            // window.location.replace('http://localhost:8081/logIn.html');
        }
        $scope.declaration = JSON.parse(localStorage.getItem("_declaration_read"));
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

    $scope.Accept = function (){
        console.log("Method: Accept ");
        console.log($scope.declaration);
        const url = contextPath+"/declaration/access/"+$scope.declaration.id;
        $http.put(url)
            .then(function (response) {
            });
        // window.location.replace('http://localhost:8081/someProfile.html');
    }
    $scope.Refuse = function (){
        console.log("Method: Refuse ");
        console.log($scope.declaration);
        const url = contextPath+"/declaration/refuse/"+$scope.declaration.id;
        $http.put(url)
            .then(function (response) {
            });
        // window.location.replace('http://localhost:8081/someProfile.html');
    }


    $scope.getUser()
});
