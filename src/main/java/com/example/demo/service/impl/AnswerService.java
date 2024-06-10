package com.example.demo.service.impl;

import com.example.demo.dto.AnswerDto;
import com.example.demo.exception.AnswerNotFoundException;
import com.example.demo.repository.AnswerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public AnswerDto getAnswer(){
        return AnswerDto.valueOf(answerRepository.findById(1L).orElseThrow(()->new AnswerNotFoundException(1L)));
    }


}
