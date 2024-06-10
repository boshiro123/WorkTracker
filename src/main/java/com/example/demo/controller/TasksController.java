package com.example.demo.controller;

import com.example.demo.dto.TasksDto;
import com.example.demo.service.impl.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v2/task")
public class TasksController {

    private final TasksService tasksService;

    @Autowired
    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void giveTask(@RequestBody @Valid TasksDto tasksDto) {
        System.out.println("TasksDto: "+tasksDto);
        tasksService.add(tasksDto);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<TasksDto> getAll(){
        return tasksService.getAll();
    }

    @GetMapping("/getMyTasks")
    @ResponseStatus(HttpStatus.OK)
    public List<TasksDto> getMyTasks(@RequestParam("user_id") Long id, @RequestParam("date") String date){
        System.out.println("------Metod: getMyTasks ---------");
        System.out.println("User id: " + id);
        System.out.println("Date: " + parseDateString(date));
        return tasksService.getAMyTasks(id,parseDateString(date));

    }
    @PutMapping("/doTask")
    @ResponseStatus(HttpStatus.OK)
    public void doTask(@RequestBody TasksDto task){
        System.out.println("------Metod: doTask ---------");
        System.out.println(task);
        tasksService.updateTask(task);
    }
    @GetMapping("/labels/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getLabelsLine(@PathVariable Long id){
        return tasksService.getLabelsLine(id);
    }
    @GetMapping("/data/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getDataLine(@PathVariable Long id){
        return tasksService.getDataLine(id);
    }


    @DeleteMapping("/deleteTask")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@RequestParam("id") Long id){
        System.out.println("Task id for delete: " + id);
        tasksService.deleteTask(id);
    }

    public static LocalDate parseDateString(String dateString) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate parsedDate = LocalDate.parse(dateString, inputFormatter);
            LocalDate incrementedDate = parsedDate.plusDays(1);

            String formattedDate = outputFormatter.format(incrementedDate);

            return LocalDate.parse(formattedDate, outputFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
