package com.example.demo.controller;

import com.example.demo.dto.ResultDto;
import com.example.demo.dto.TestDto;
import com.example.demo.service.impl.ResultService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v2/result")
public class ResultController {
    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveResult(@RequestBody @Valid ResultDto resultDto) {
        System.out.println(resultDto);
        resultService.save(resultDto);
    }
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ResultDto> getResults(@PathVariable Long id){
        return resultService.getResults(id);
    }
    @GetMapping("/labels/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getLabels(@PathVariable Long id){
        return resultService.getLabels(id);
    }
    @GetMapping("/data/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Double> getData(@PathVariable Long id){
        return resultService.getData(id);
    }
    @GetMapping("/avarage/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Double getAvarageScore(@PathVariable Long id){
        return  resultService.getAvarageScore(id);
    }

}
