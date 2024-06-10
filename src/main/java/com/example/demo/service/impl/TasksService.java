package com.example.demo.service.impl;

import com.example.demo.dto.TasksDto;
import com.example.demo.entity.Tasks;
import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.UserRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private final UserRepository userRepository;

    @Autowired
    public TasksService(TasksRepository tasksRepository, UserRepository userRepository) {
        this.tasksRepository = tasksRepository;
        this.userRepository = userRepository;
    }


    public void add(TasksDto tasksDto){
        tasksDto.setDate(tasksDto.getDate().plusDays(1));
        tasksDto.setStatus("Не выполнено");
        tasksDto.setUser_name(userRepository.findByUser_id(tasksDto.getUser_id()).getName());
        System.out.println(tasksDto);
        Tasks task = tasksDto.mapTo();
        tasksRepository.save(task);
    }

    public List<TasksDto> getAll(){
        List<TasksDto> tasksList = TasksDto.ListValueOf(tasksRepository.findAll());
        return tasksList;
    }
    public List<TasksDto> getAMyTasks(Long user_id, LocalDate date){
        List<TasksDto> tasksList = TasksDto.ListValueOf(tasksRepository.findAllTasks(user_id, date));
        return tasksList;
    }
    public void updateTask(TasksDto task){
        task.setStatus("Выполнено");
        Tasks updateTask = task.mapTo();
        tasksRepository.save(updateTask);
    }
    public void deleteTask(Long id){
        tasksRepository.deleteById(id);
    }
    public List<String> getLabelsLine(Long id){
        List<Date> tasksList = tasksRepository.findAllDatesFromUser(id);
        List<String> labels = new ArrayList<>();
        for (Date i: tasksList){
            labels.add(i.toString());
        }
        return labels;
    }
    public List<Integer> getDataLine(Long id){
        int count = 0;
        LocalDate currentDate = null;
        List<TasksDto> tasksList = TasksDto.ListValueOf(tasksRepository.findAllByUser_idOrderByDate(id));
        List<Integer> data = new ArrayList<>();
        for (TasksDto task : tasksList) {
            if (!task.getDate().equals(currentDate)) {
                // Дата сменилась, выводим количество выполненных задач для предыдущей даты
                if (currentDate != null) {
//                    System.out.println("Дата: " + currentDate + ", Количество выполненных задач: " + count);
                    data.add(count);
                }

                // Сбрасываем счетчик и обновляем текущую дату
                count = 0;
                currentDate = task.getDate();
            }

            if ("Выполнено".equals(task.getStatus())) {
                // Увеличиваем счетчик выполненных задач
                count++;
            }
        }
        // Вывод результатов для последней даты
        if (currentDate != null) {
//            System.out.println("Дата: " + currentDate + ", Количество выполненных задач: " + count);
            data.add(count);
        }
        return data;
    }
}
