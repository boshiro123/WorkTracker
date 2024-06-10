package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="user_id")
    private Long user_id;
    @Column(name="user_name")
    private String user_name;
    @Column(name="task")
    private String task;
    @Column(name="date")
    private LocalDate date;
    @Column(name="status")
    private String status;

    public Tasks(Long user_id, String user_name, String task, LocalDate date, String status) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.task = task;
        this.date = date;
        this.status = status;
    }
}
