package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "declaration")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Declaration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "fio")
    private String fio;
    @Column(name = "text")
    private String text;
    @Column(name="date_from")
    private LocalDate from;
    @Column(name="date_to")
    private LocalDate to;
    @Column(name="date")
    private LocalDate date;
    @Column(name="status")
    private String status;

    public Declaration(Long userId, String fio, String text, LocalDate from, LocalDate to, LocalDate date, String status) {
        this.userId = userId;
        this.fio = fio;
        this.text = text;
        this.from = from;
        this.to = to;
        this.date = date;
        this.status = status;
    }
}
