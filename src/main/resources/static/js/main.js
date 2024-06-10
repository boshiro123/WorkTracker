
angular.module('main',[]).controller("MainController",function ($scope,$http){
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
    $scope.makeVacation=function (){
        console.log("Оформить заявление")
        localStorage.setItem("_declaration", JSON.stringify("Прошу предоставить мне ежегодный оплачиваемый отпуск с [дата от и до] с сроком на N календарных дней"));
        window.location.replace('http://localhost:8081/declaration.html');
    };
    $scope.makeMedical=function (){
        console.log("Оформить заявление")
        localStorage.setItem("_declaration", JSON.stringify("Прошу предоставить мне больничный отпуск с [дата от и до] с сроком на N календарных дней по причине плохого самочувствия"));
        window.location.replace('http://localhost:8081/declaration.html');
    };
    $scope.FreeForm=function (){
        console.log("Оформить заявление")
        localStorage.setItem("_declaration", JSON.stringify(""));
        window.location.replace('http://localhost:8081/declaration.html');
    };

    $scope.checkTime=function (){
        const url=contextPath+"/time/checkTime";
        document.getElementById('start').disabled = false;
        document.getElementById('start_time').value = "";
        document.getElementById('end').disabled = false;
        document.getElementById('end_time').value = "";
        $http({
            url: url,
            method: 'GET',
            params:{
                Date: $scope.ToDayDate,
                user_id: $scope.User.id
            }
        }).then(function (response){
            console.log(response.data);
            $scope.getMyTasks()
            if (response.data.id == null){
                console.log("Вы не работали в этот день")
            }
            else {
                // alert("Вы уже работали в этот день")
                if (response.data.startTime != null) {
                    document.getElementById('start_time').value = response.data.startTime;
                    document.getElementById('start').disabled = true;

                }
                if (response.data.endTime != null) {
                    document.getElementById('end_time').value = response.data.endTime;
                    document.getElementById('end').disabled = true;
                }
            }
        });
    };

    $scope.addTime=function (){
        const url=contextPath+"/time/addTime";
        $scope.StartTime = startTime()
        $http({
            url: url,
            method: 'POST',
            params:{
                Date: $scope.ToDayDate,
                StartTime: startTime(),
                user_id: $scope.User.id
            }
        }).then(function (response){
            console.log(typeof response.data)
            console.log( response.data)
            if(response.data){
                document.getElementById('start').disabled = true;
            }
            else{
                console.log("Что-то пошло не так")
            }
        });
    };
    $scope.getUsers=function (){
        const url=contextPath+"/user/all";
        let i=0;
        $http.get(url)
            .then(function (response){
                $scope.Users=response.data;
                console.log($scope.Users);
            });
    };
    $scope.updateTime=function (){
        const url=contextPath+"/time/updateTime";
        $scope.EndTime = endTime()
        $http({
            url: url,
            method: 'PUT',
            params:{
                Date: $scope.ToDayDate,
                EndTime: endTime(),
                user_id: $scope.User.id
            }
        }).then(function (response){
            console.log(typeof response.data)
            console.log( response.data)
            if(response.data){
                document.getElementById('end').disabled = true;
            }
            else{
                console.log("Что-то пошло не так")
            }
        });
    };

    $scope.getTasks=function (){
        const url=contextPath+"/task/getAll";
        $http.get(url)
            .then(function (response){
                console.log(response.data);
                $scope.Tasks=response.data;
            });
    };

    $scope.DeleteTask=function (task){
        const url=contextPath+"/task/deleteTask";
        $http({
            url: url,
            method: 'DELETE',
            params:{
                id: task.id
            }
        }).then(function (response){
            $scope.getTasks()
        });
    };

    $scope.GiveTask=function (){
        const url = contextPath + '/task/add';
        $scope.Task.user_id = $scope.user.id
        $scope.Task.date = $scope.TaskDate
        console.log($scope.Task)
        $http.post(url, $scope.Task)
        .then(function (response){
            $scope.getTasks()
        })

    }
    $scope.getMyTasks= function (){
        const url = contextPath + '/task/getMyTasks';
        $http({
            url: url,
            method: 'GET',
            params:{
                user_id: $scope.User.id,
                date: $scope.ToDayDate
            }
        }).then(function (response){
            $scope.myTasks = response.data
            for(i=0;i<$scope.myTasks.length;i++) {
            if ($scope.myTasks[i].status === "Выполнено"){
                $scope.myTasks[i].disabled=true;
            }
            else $scope.myTasks[i].disabled=false;
            }
                console.log($scope.myTasks)
        });

    }
    $scope.DoTask = function(task) {
        const url = contextPath + '/task/doTask';
        task.disabled = true;
        console.log(task);
        $http.put(url, task)
            .then(function (response){

            })
        // var checkboxElement = document.getElementById(task.id);
        // checkboxElement.style.backgroundColor = "#0b76ef";
    }

    $scope.ShowDate = function (){
        console.log($scope.ToDayDate)
    }
    $scope.getUser()
    $scope.getTasks()
    $scope.getUsers()

});

function startTime() {
    var today = new Date();
    var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    console.log(today)
    document.getElementById('start_time').value = time;
    return time
}

function endTime() {
    var today = new Date();
    var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    console.log(today)
    document.getElementById('end_time').value = time;
    return time
}