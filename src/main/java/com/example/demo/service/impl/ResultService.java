package com.example.demo.service.impl;

import com.example.demo.dto.ResultDto;
import com.example.demo.entity.Result;
import com.example.demo.entity.Test;
import com.example.demo.exception.TestNotFoundException;
import com.example.demo.repository.ResultRepository;
import com.example.demo.repository.TestRepository;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {
    private final ResultRepository resultRepository;
    private final TestRepository testRepository;

    @Autowired
    public ResultService(ResultRepository resultRepository, TestRepository testRepository) {
        this.resultRepository = resultRepository;
        this.testRepository = testRepository;
    }

    public void save(ResultDto resultDto){
        Result result = resultDto.mapTo();
        Test test = testRepository.findById(resultDto.getTest().getId()).orElseThrow(()-> new TestNotFoundException(resultDto.getTest().getId()));
        result.setTest(test);
        Result res = resultRepository.findByUser_id(resultDto.getUser_id(),resultDto.getTest().getId());
        if(res==null){
            System.out.println("Тест не был пройден");
            resultRepository.save(result);
        }
        else{
            System.out.println("Тест уже был пройден");
            resultRepository.updateByUser_id(resultDto.getResult(),resultDto.getUser_id(), resultDto.getTest().getId());
        }

    }
    public List<ResultDto> getResults(Long id){
        List<ResultDto> list = ResultDto.ListvalueOf(resultRepository.findAllByUser_id(id));
        return list;
    }
    public List<String> getLabels(Long id){
        List<ResultDto> list = ResultDto.ListvalueOf(resultRepository.findAllByUser_id(id));
        List<String> labels = new ArrayList<>();
        for(ResultDto i: list){
            labels.add(i.getTest().getTitle());
        }
        return labels;
    }
    public List<Double> getData(Long id){
        List<ResultDto> list = ResultDto.ListvalueOf(resultRepository.findAllByUser_id(id));
        List<Double> labels = new ArrayList<>();
        for(ResultDto i: list){
            labels.add(i.getResult());
        }
        return labels;
    }
    public Double getAvarageScore(Long id){
        Double score = resultRepository.averageScore(id);
        return score;
    }
}
