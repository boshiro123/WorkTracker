package com.example.demo.dto;

import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long id;
    private String question;
    private Answer answer;
    private Long test_id;


    public static QuestionDto valueOf(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getQuestion(),
                question.getAnswer(),
                question.getId_test()
        );
    }
    public static List<QuestionDto> ListvalueOf(List<Question> questions) {
        List<QuestionDto> list = new ArrayList<>();
        for(Question i: questions) {
            list.add(valueOf(i));
        }
        return list;
    }
    public Question mapTo() {
        return new Question(id,question,answer,test_id);
    }

}
