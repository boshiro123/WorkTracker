package com.example.demo.service.impl;

import com.example.demo.dto.QuestionDto;
import com.example.demo.dto.TestDto;
import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public TestService(TestRepository testRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Long saveTest(TestDto testDto) {
        testRepository.save(testDto.mapTo());
        TestDto id = TestDto.valueOf(testRepository.findByTitle(testDto.getTitle()));
        return id.getId();
    }

    public List<TestDto> fingAll() {
        List<TestDto> list = TestDto.ListvalueOf(testRepository.findAll());
        return list;
    }

    public void delete(Long id) {
        testRepository.deleteById(id);
        List<QuestionDto> questions = QuestionDto.ListvalueOf(questionRepository.findAllById_test(id));
        questionRepository.deleteById_test(id);
        for (QuestionDto i : questions) {
            answerRepository.DeleteById(i.getAnswer().getId());
        }
        System.out.println("Всё удалено");
    }
}
