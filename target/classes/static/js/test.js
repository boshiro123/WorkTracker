angular.module('test', []).controller("TestController", function ($scope, $http) {
    const contextPath = 'http://localhost:8081/api/v2'
    $scope.i=0;
    $scope.quest={};
    $scope.answer="";
    $scope.present=0;
    $scope.score=0;
    $scope.Test={};
    $scope.getTest = function () {
        $scope.Test = JSON.parse(localStorage.getItem("_test"));
        if($scope.Test==null){
            // window.location.replace('http://localhost:8081/logIn.html');
        }
        console.log("Test id: " + $scope.Test.id);
        const url = contextPath + "/test/question/" + $scope.Test.id;
        $http.get(url)
            .then(function (response) {
                $scope.Questions=response.data;
                console.log($scope.Questions);
                $scope.Length = $scope.Questions.length;
            });
      //  $scope.next();
    };
    $scope.getUser=function (){
        $scope.User = JSON.parse(localStorage.getItem("_user"));
        if($scope.User==null){
            window.location.replace('http://localhost:8081/logIn.html');
        }
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
    $scope.next=function (){
        if($scope.answer!="") {
            if (($scope.i + 1) < $scope.Length) {
                console.log($scope.answer);
                console.log("Correct answer: " + $scope.Questions[$scope.i].answer.correct);
                if ($scope.answer === $scope.Questions[$scope.i].answer.correct) $scope.score += 100 / $scope.Length;
                $scope.i++;
                $scope.present = 100 / $scope.Length * $scope.i;
                console.log($scope.answer);
                document.getElementById('1answer').checked = false;
                document.getElementById('2answer').checked = false;
                document.getElementById('3answer').checked = false;
            } else if (($scope.i + 1) >= $scope.Length) {
                console.log("Test закончен")
                if ($scope.answer === $scope.Questions[$scope.i].answer.correct) $scope.score += 100 / $scope.Length;
                console.log("Score: " + $scope.score);
                console.log($scope.Test)
                let result = {
                    result: $scope.score,
                    test: $scope.Test,
                    user_id: $scope.User.id
                };
                const url = contextPath + "/result";
                $http.post(url, result)
                    .then(function (response) {
                        alert("Тест пройден!!!\nРезультаты можете увидеть в профиле");
                        window.location.replace('http://localhost:8081/profile.html');
                    });
            }
            console.log("Score: " + $scope.score);
            $scope.answer="";
        }
        else {
            alert("Ответ не выбран!")
        }
    };
    $scope.showInfo = function (answer){
        $scope.answer=answer;
    };
    $scope.getUser();
    $scope.getTest();
});

