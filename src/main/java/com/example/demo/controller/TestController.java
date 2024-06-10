package com.example.demo.controller;

import com.example.demo.dto.AnswerDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.dto.TestDto;
import com.example.demo.service.impl.AnswerService;
import com.example.demo.service.impl.QuestionService;
import com.example.demo.service.impl.TestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v2/test")
public class TestController {
    private final AnswerService answerService;

    private final QuestionService questionService;

    private final TestService testService;

    @Autowired
    public TestController(AnswerService answerService, QuestionService questionService, TestService testService) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.testService = testService;
    }

    @GetMapping("/tests")
    private List<TestDto> getTests(){
        return testService.fingAll();
    }
    @GetMapping("/question/{id}")
    private List<QuestionDto> getTest(@PathVariable("id") Long id){
        return questionService.getQuestions(id);
    }


    @GetMapping("/answer")
    private AnswerDto getAnswer() {
        return answerService.getAnswer();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long saveTest(@RequestBody @Valid TestDto testDto) {
        return testService.saveTest(testDto);
    }

    @PostMapping("/questions/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveQuestions(@PathVariable("id") Long id, @RequestBody @Valid List<QuestionDto> questionDtos) {
        questionService.saveQuestion(id,questionDtos);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Удалить товар")
    public void deleteQuestion(@PathVariable("id") Long id) {
        testService.delete(id);
    }


}
