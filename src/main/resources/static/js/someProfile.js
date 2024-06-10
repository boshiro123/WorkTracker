angular.module('someProfile',[]).controller("someProfileController",function ($scope,$http){
    const contextPath = 'http://localhost:8081/api/v2'
    $scope.labels = []
    $scope.data = []

    $scope.labelsLine = []
    $scope.dataLine = []
    $scope.getUser=function (){
        $scope.User = JSON.parse(localStorage.getItem("_some_user"));
        $scope.UserMain = JSON.parse(localStorage.getItem("_user"));
        if($scope.User==null){
            // window.location.replace('http://localhost:8081/logIn.html');
        }
        console.log($scope.User);
        console.log(typeof $scope.User);
        if($scope.User.user_info.male == "Мужчина")$scope.MalePage="https://losprintos.ru/wp-content/uploads/2020/12/placeholder-man-2048x2048.png";
        else if($scope.User.user_info.male == "Женщина")$scope.MalePage="https://cdn1.ozone.ru/s3/multimedia-5/6051392309.jpg";
        //localStorage.setItem("_user", JSON.stringify($scope.User));
        if ($scope.UserMain.status.name == "common") {
            $scope.TypeTests="tests.html";
            $scope.TypeMain = "main.html"
        } else if ($scope.UserMain.status.name == "admin") {
            $scope.TypeTests="AdminTests.html";
            $scope.TypeMain = "AdminMain.html"
            document.getElementById("WorkResults").style.display='none';

        }

    };
    $scope.getResults=function (){
        const url = contextPath+"/result/id/"+$scope.User.id;
        $http.get(url)
            .then(function (resp) {
                $scope.Results = resp.data;
                console.log($scope.Results)
            });
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
    $scope.openDeclaration=function (dec){
        console.log("Method openDeclaration")
        localStorage.setItem("_declaration_read", JSON.stringify(dec));
        window.location.replace('http://localhost:8081/read_declaration.html');
    };
    $scope.SendMessage = function (){
        console.log("Send Message: " + $scope.Message);
        $scope.User.user_info.message=$scope.Message;
        console.log($scope.User);
        const url=contextPath+"/user/updateMessage";
        $http.put(url,$scope.User)
            .then(function (response){
            });

    }
    $scope.getLabels=function (){
        const url = contextPath+"/result/labels/id/"+$scope.User.id;
        $http.get(url)
            .then(function (resp) {
                $scope.Labels = resp.data;
                console.log("LABELS")
                console.log($scope.Labels);
                $scope.labels = resp.data
                $scope.getData()
            });

    };
    $scope.getData=function (){
        const url = contextPath+"/result/data/id/"+$scope.User.id;
        $http.get(url)
            .then(function (resp) {
                $scope.Labels = resp.data;
                console.log("Data")
                console.log($scope.Labels);
                $scope.data = resp.data
                $scope.createBar()
            });

    };
    $scope.createBar=function () {
        console.log($scope.labels)
        console.log($scope.data)

        // Создаем график
        var ctx = document.getElementById('firstChart').getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: $scope.labels,
                datasets: [{
                    label: 'Тесты',
                    data: $scope.data,
                    backgroundColor: 'rgba(75,112,192,0.6)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                width: 400,
                height: 300,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
    $scope.getLabelsLine=function (){
        const url = contextPath+"/task/labels/id/"+$scope.User.id;
        $http.get(url)
            .then(function (resp) {
                console.log("LABELS")
                $scope.labelsLine = resp.data
                $scope.getDataLine()
            });

    };
    $scope.getDataLine=function (){
        const url = contextPath+"/task/data/id/"+$scope.User.id;
        $http.get(url)
            .then(function (resp) {
                console.log("Data")
                $scope.dataLine = resp.data
                $scope.createLine()
            });

    };

    $scope.createLine=function () {
        console.log($scope.labelsLine)
        console.log($scope.dataLine)

        // Создаем график
        var ctx = document.getElementById('secondChart').getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: $scope.labelsLine,
                datasets: [{
                    label: 'Задачи',
                    data: $scope.dataLine,
                    backgroundColor: 'rgba(75,112,192,0.6)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                width: 400,
                height: 300,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
    $scope.getAnaliz=function (){
        const url = contextPath+"/time/id/"+$scope.User.id;
        $http.get(url)
            .then(function (response) {
                console.log(response.data)
                $scope.Analiz = response.data.result.replace(/\n/g, '<br>');
                // Находим элемент на HTML странице по его id
                let element = document.getElementById('analiz');

// Вставляем содержимое переменной htmlResult в элемент
                element.innerHTML = $scope.Analiz;
            });

    };
    $scope.getWorkDays=function (){
        const url = contextPath+"/time/workDays/"+$scope.User.id;
        $http.get(url)
            .then(function (response) {
                console.log("Method: getWorkDays-----")
                console.log(response.data)
                $scope.days=response.data
            });

    };
    $scope.updateProfile = function () {
        const url = contextPath + '/user/update';
        console.log("Method updateProfile(), url: " + url + ", User=" + $scope.User);
        $http.put(url, $scope.User)
            .then(function (resp) {
            });

    };

    $scope.createDocx = function () {
        const url = contextPath + '/time/createReport/' + $scope.User.id;
        console.log("Method createDocx(), url: " + url + ", User=" + $scope.User);
        $http.get(url)
            .then(function (resp) {
                alert("Отчёт создан")
            });

    };


    $scope.getUser();
    $scope.getResults();
    $scope.getLabels()
    $scope.getLabelsLine()
    $scope.getAnaliz();
    $scope.getDeclarations();
    $scope.getWorkDays();


});
