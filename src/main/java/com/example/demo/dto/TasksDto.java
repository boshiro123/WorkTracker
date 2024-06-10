package com.example.demo.dto;

import com.example.demo.entity.Tasks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TasksDto {

    private Long id;
    private Long user_id;
    private String user_name;
    private String task;
    private LocalDate date;
    private String status;

    public static TasksDto valueOf(Tasks task) {
        return new TasksDto(
                task.getId(),
                task.getUser_id(),
                task.getUser_name(),
                task.getTask(),
                task.getDate(),
                task.getStatus()
        );
    }

    public Tasks mapTo() {
        return new Tasks(id,user_id,user_name,task,date,status);
    }

    public static List<TasksDto> ListValueOf(List<Tasks> tasks) {
        List<TasksDto> list = new ArrayList<>();
        for(Tasks i: tasks) {
            list.add(valueOf(i));
        }
        return list;
    }
}
