
angular.module('tests',[]).controller("MainController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2';
    $scope.getUser=function () {
        $scope.User = JSON.parse(localStorage.getItem("_user"));
        if($scope.User==null){
            // window.location.replace('http://localhost:8081/logIn.html');
        }
        console.log(typeof $scope.User);
        console.log($scope.User);

        if ($scope.User.status.name === "common") {
            $scope.TypeTests="tests.html";
            $scope.TypeMain = "main.html"
        } else if ($scope.User.status.name === "admin") {
            $scope.TypeTests="AdminTests.html";
            $scope.TypeMain = "AdminMain.html"
            document.getElementById("WorkResults").style.display='none';

        }
    };

    $scope.getTests=function (){
        const url=contextPath+"/test/tests";
        let i=0;
        $http.get(url)
            .then(function (response){
                console.log(response.data);
                $scope.Tests=response.data;
            });
    };
    $scope.TakeTest=function (test){
        localStorage.setItem("_test", JSON.stringify(test));
        window.location.replace('http://localhost:8081/test.html');
    };
    $scope.openInfo=function (test){
        localStorage.setItem("_test_info", JSON.stringify(test));
        window.location.replace('http://localhost:8081/testInfo.html');
    };
    $scope.deleteQuestion=function (test){
        const url=contextPath+"/test/"+test.id;
        $http.delete(url)
            .then(function (response){
                location.reload();
            });
        $scope.getTests();
    };
    $scope.getUser();
    $scope.getTests();
});
