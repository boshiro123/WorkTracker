angular.module('testInfo',[]).controller("TestInfoController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2';
    $scope.Test = JSON.parse(localStorage.getItem("_test_info"));
    $scope.User = JSON.parse(localStorage.getItem("_user"));

    if ($scope.User.status.name == "common") {
        $scope.TypeTests="tests.html";
        $scope.TypeMain = "main.html"
    } else if ($scope.User.status.name == "admin") {
        $scope.TypeTests="AdminTests.html";
        $scope.TypeMain = "AdminMain.html"
        document.getElementById("WorkResults").style.display='none';

    }
    console.log($scope.Test);
    console.log($scope.User);
    $scope.getQuestions=function (){
        const url = contextPath+"/test/question/"+$scope.Test.id;
        $http.get(url)
            .then(function (response){
                $scope.Questions=response.data;
                console.log($scope.Questions);
                for(let i=0;i<$scope.Questions.length; i++){
                    $scope.Questions[i].NumberOfQuestion = i+1;
                }
            });
    };
    $scope.getAvarageScore=function (){
        const url = contextPath+"/result/avarage/"+$scope.Test.id;

        $http.get(url)
            .then(function (response){
                $scope.Avarage=response.data;
            });
    };
    $scope.getQuestions();
    $scope.getAvarageScore();
});
