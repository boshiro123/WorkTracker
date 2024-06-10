package com.example.demo.service.impl;

import com.example.demo.dto.QuestionDto;
import com.example.demo.entity.Question;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDto> getQuestions(Long id){
        List<QuestionDto> list = QuestionDto.ListvalueOf(questionRepository.findAllById_test(id));
        return list;
    }

    public void saveQuestion(Long id,List<QuestionDto> questionDtos){
        for(QuestionDto i: questionDtos){
            Question question = i.mapTo();
            question.setId_test(id);
            questionRepository.save(question);
        }
    }
}
