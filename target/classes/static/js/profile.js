angular.module('profile',[]).controller("ProfileController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2'
    $scope.getUser=function (){
        $scope.User = JSON.parse(localStorage.getItem("_user"));
        if($scope.User==null){
            // window.location.replace('http://localhost:8081/logIn.html');
        }
        console.log(typeof $scope.User);
        console.log($scope.User);
        console.log(typeof $scope.User);
        if($scope.User.user_info.male == "Мужчина")$scope.MalePage="https://losprintos.ru/wp-content/uploads/2020/12/placeholder-man-2048x2048.png";
        else if($scope.User.user_info.male == "Женщина")$scope.MalePage="https://cdn1.ozone.ru/s3/multimedia-5/6051392309.jpg";
        // const url=contextPath+"/user/info/"+$scope.User.id;

        if($scope.User.status.name ==="common"){
            document.getElementById("CreateTestButton").style.display='none';
            $scope.TypeTests="tests.html";
            $scope.TypeMain = "main.html"
            $scope.TypeUserList="";
        }
        else if($scope.User.status.name==="admin"){
            document.getElementById("AdminMessage").style.display='none';
            document.getElementById("ResultsId").style.display='none';
            document.getElementById("WorkResults").style.display='none';
            $scope.TypeTests="AdminTests.html";
            $scope.TypeUserList="Список участников";
            $scope.TypeMain = "AdminMain.html"


        }
    };

    $scope.getResults=function (){
        const url = contextPath+"/result/id/"+$scope.User.id;
        $http.get(url)
            .then(function (resp) {
                $scope.Results = resp.data;
                console.log("RESULTS")
                console.log($scope.Results);
            });
    };

    $scope.changeUser = function () {
        const url = contextPath + '/user/update';
        console.log("Method changeUser(), url: " + url + ", User=" + $scope.User);
        $http.put(url, $scope.User)
            .then(function (resp) {
                localStorage.clear();
                localStorage.setItem("_user", JSON.stringify($scope.User));
            });
    };

    $scope.openCreater=function (){
        window.location.replace("http://localhost:8081/TestCreate.html");
    };
    $scope.getDeclarations=function (){
            const url = contextPath+"/declaration/getDeclarations/id/"+$scope.User.id;
            console.log("Method: getDeclarations")
            $http.get(url)
                .then(function (response) {
                    $scope.Declarations = response.data;
                    console.log(response.data)
                });
        };

    $scope.Out=function (){
        window.location.replace("http://localhost:8081/index.html");
    };
    $scope.getUser();
    $scope.getResults();
    $scope.getDeclarations()


});



const showOrHidePassword = () => {
    const password = document.getElementById('password');
    if (password.type === 'password') {
        password.type = 'text';
    } else {
        password.type = 'password';
    }
};