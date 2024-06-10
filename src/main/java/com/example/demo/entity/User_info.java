package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User_info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "experience")
    private Double experience;
    @Column(name = "city")
    private String city;
    @Column(name = "position")
    private String position;
    @Column(name = "male")
    private String male;
    @Column(name = "message")
    private String message;
    @Column(name = "bet")
    private Double bet;
    @JsonIgnore
    @OneToMany(mappedBy = "user_info", cascade = CascadeType.ALL)
    private List<User> users;

    public User_info(Long id, Double experience, String city, String position, String male, String message, Double bet) {
        this.id = id;
        this.experience = experience;
        this.city = city;
        this.position = position;
        this.male = male;
        this.message = message;
        this.bet=bet;
    }
}
