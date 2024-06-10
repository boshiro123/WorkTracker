angular.module('chartApp', []).controller('ChartController', function($scope,$http) {
    const contextPath = 'http://localhost:8081/api/v2'
    $scope.labels = []
    $scope.data = []

    $scope.labelsLine = []
    $scope.dataLine = []

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
            $scope.TypeTests="tests.html";
            $scope.TypeMain = "main.html"
            $scope.TypeUserList="";
        }
        else if($scope.User.status.name==="admin"){
            document.getElementById("WorkResults").style.display='none';
            $scope.TypeTests="AdminTests.html";
            $scope.TypeUserList="Список участников";
            $scope.TypeMain = "AdminMain.html"


        }
    };
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
    $scope.getWorkHours=function (){
            const url = contextPath+"/time/getWorkHours/id/"+$scope.User.id;
            $http.get(url)
                .then(function (resp) {
                    console.log("GetWorkHours")
                    $scope.hours = resp.data
                    console.log($scope.hours)
                    $scope.getWorkDates()
                });

        };
     $scope.getWorkDates=function (){
                const url = contextPath+"/time/getWorkDates/id/"+$scope.User.id;
                $http.get(url)
                    .then(function (resp) {
                        console.log("getWorkDates")
                        $scope.workDates = resp.data
                        console.log($scope.workDates)
                        $scope.createThirdBar()
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
    $scope.createThirdBar=function () {

            // Создаем график
            var ctx = document.getElementById('thirdChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: $scope.workDates,
                    datasets: [{
                        label: 'Даты',
                        data: $scope.hours,
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

    $scope.getUser()
    $scope.getLabels()
    $scope.getLabelsLine()
    $scope.getAnaliz()
    $scope.getWorkHours()

});