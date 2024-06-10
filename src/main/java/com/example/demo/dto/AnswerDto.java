package com.example.demo.dto;

import com.example.demo.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    private Long id;
    private String answer1;
    private String answer2;
    private String answer3;
    private String correct;

    public static AnswerDto valueOf(Answer answer) {
        return new AnswerDto(
                answer.getId(),
                answer.getAnswer1(),
                answer.getAnswer2(),
                answer.getAnswer3(),
                answer.getCorrect()
        );
    }
    public Answer mapTo() {
        return new Answer(id,answer1,answer2,answer3,correct);
    }
}
