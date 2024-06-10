
angular.module('articles',[]).controller("ArticlesController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2';

    $scope.getUser=function () {
        $scope.User = JSON.parse(localStorage.getItem("_user"));
        if($scope.User==null){
            // window.location.replace('http://localhost:8081/logIn.html');
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
            $scope.TypeUserList="Список участников";


        }
    };

    $scope.getArticles=function (){
        const url = contextPath + "/articles/getAll";
        $http.get(url)
            .then(function (response) {
                $scope.Articles= response.data
                console.log(response.data)
            });

    }




    $scope.getUser()
    $scope.getArticles()

});
