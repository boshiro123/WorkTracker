angular.module('testCreate',[]).controller("TestCreateController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2'
    $scope.NumberOfQuestion=1;
    $scope.Questions=[];
    $scope.Question={};

    $scope.NextQuestion=function (){
        if($scope.Question.question!=null && $scope.Question.answer.answer2!=null &&
            $scope.Question.answer.answer3!=null &&  $scope.Question.answer.correct!=null) {
            $scope.NumberOfQuestion++;
            $scope.Questions.push($scope.Question)
            console.log($scope.Questions)
            console.log($scope.Question)
            $scope.Question = null;
        }
        else{
            alert("Не все поля заполены!")
        }
    }
    $scope.SaveTest=function (){
        if ($scope.Test.passing_score>=0 && $scope.Test.passing_score<=100 && $scope.Test.title!=null && $scope.Test.user_category!=null) {
            const url = contextPath + "/test"
            $http.post(url, $scope.Test)
                .then(function (response) {
                    const url2 = url + "/questions/" + response.data;
                    console.log(url2)
                    $http.post(url2, $scope.Questions)
                        .then(function (response) {
                            window.location.replace('http://localhost:8081/AdminMain.html');

                                });
                });
            console.log("It works :)")
        }
        else{
            alert("Не вся информация о тесте заполнена!")
        }

    };
    $scope.getUser=function (){
        $scope.User = JSON.parse(localStorage.getItem("_user"));
        if($scope.User==null){
            window.location.replace('http://localhost:8081/logIn.html');
        }
        console.log(typeof $scope.User);
        console.log($scope.User);

        if ($scope.User.status.name == "common") {
            $scope.TypeTests="tests.html"
            $scope.TypeMain = "main.html"
        } else if ($scope.User.status.name == "admin") {
            $scope.TypeTests="AdminTests.html"
            $scope.TypeMain = "AdminMain.html"
            document.getElementById("WorkResults").style.display='none';

        }
    };
    $scope.getUser();
});
