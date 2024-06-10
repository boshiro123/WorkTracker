package com.example.demo.dto;

import com.example.demo.entity.Test;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {

    private Long id;
    private String title;
    private String user_category;
    private int passing_score;


    public static TestDto valueOf(Test test) {
        return new TestDto(
                test.getId(),
                test.getTitle(),
                test.getUser_category(),
                test.getPassing_score()
        );
    }

    public static List<TestDto> ListvalueOf(List<Test> tests) {
        List<TestDto> list = new ArrayList<>();
        for(Test i: tests) {
            list.add(valueOf(i));
        }
        return list;
    }
    public Test mapTo() {
        return new Test(id,title,user_category,passing_score);
    }
}
