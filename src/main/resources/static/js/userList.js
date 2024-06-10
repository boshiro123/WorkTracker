angular.module('userList',[]).controller("UserListController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2'
    $scope.getUser=function (){
        $scope.User = JSON.parse(localStorage.getItem("_user"));
        if($scope.User==null && $scope.User.status.name=="common"){
            // window.location.replace('http://localhost:8081/logIn.html');
        }
        console.log(typeof $scope.User);
        console.log($scope.User);
        console.log(typeof $scope.User);
        if($scope.User.user_info.male == "Мужчина")$scope.MalePage="https://losprintos.ru/wp-content/uploads/2020/12/placeholder-man-2048x2048.png";
        else if($scope.User.user_info.male == "Женщина")$scope.MalePage="https://cdn1.ozone.ru/s3/multimedia-5/6051392309.jpg";
        //localStorage.setItem("_user", JSON.stringify($scope.User));
        if ($scope.User.status.name == "common") {
            $scope.TypeTests="tests.html";
            $scope.TypeMain = "main.html"
        } else if ($scope.User.status.name == "admin") {
            $scope.TypeTests="AdminTests.html";
            $scope.TypeMain = "AdminMain.html"
            document.getElementById("WorkResults").style.display='none';

        }
    };
    $scope.getUsers=function (){
        const url=contextPath+"/user/all";
        let i=0;
        $http.get(url)
            .then(function (response){
                console.log(response.data);
                $scope.Users=response.data;
                for(i=0;i<$scope.Users.length;i++){
                    if($scope.Users[i].user_info.male=="Мужчина")$scope.Users[i].user_info.page="https://losprintos.ru/wp-content/uploads/2020/12/placeholder-man-2048x2048.png";
                    else if($scope.Users[i].user_info.male == "Женщина")$scope.Users[i].user_info.page="https://cdn1.ozone.ru/s3/multimedia-5/6051392309.jpg";
                }
                console.log($scope.Users);
            });
    };
    $scope.setButton=function (Status){
        if(Status!="block"){
            return "Заблокировать";
        }
        else if (Status=="block"){
            return "Разблокировать";
        }
    };
    $scope.changeStatus=function (user){
        const url = contextPath+"/user/block";
        $http.put(url, user)
            .then(function (responce) {
                console.log(responce.data);
                location.reload();
            });
    }
    $scope.openInfo=function (user){
        localStorage.setItem("_some_user", JSON.stringify(user));
        window.location.replace('http://localhost:8081/someProfile.html');
    };
    $scope.getUser();
    $scope.getUsers();

});

const showOrHidePassword = () => {
    const password = document.getElementById('password');
    if (password.type === 'password') {
        password.type = 'text';
    } else {
        password.type = 'password';
    }
};