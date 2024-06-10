package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "test")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;

    @Column(name = "user_category")
    private String user_category;

    @Column(name = "passing_score")
    private int passing_score;

    @JsonIgnore
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<Result> results;

    public Test(Long id, String title, String user_category, int passing_score) {
        this.id = id;
        this.title = title;
        this.user_category = user_category;
        this.passing_score = passing_score;
    }
}
