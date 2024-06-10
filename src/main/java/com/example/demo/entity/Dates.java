package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "dates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dates{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "start_time")
    private Time startTime;
    @Column(name = "end_time")
    private Time endTime;
    @Column(name = "user_id")
    private Long user_id;

    public Dates(LocalDate date, Time startTime, Long user_id) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = null;
        this.user_id = user_id;
    }
}
