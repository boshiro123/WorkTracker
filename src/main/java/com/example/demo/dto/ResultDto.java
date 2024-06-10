package com.example.demo.dto;

import com.example.demo.entity.Result;
import com.example.demo.entity.Test;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private Long id;
    private Double result;
    private Test test;
    private Long user_id;

    public static ResultDto valueOf(Result result) {
        return new ResultDto(
                result.getId(),
                result.getResult(),
                result.getTest(),
                result.getUser_id()
        );
    }
    public static List<ResultDto> ListvalueOf(List<Result> results) {
        List<ResultDto> list = new ArrayList<>();
        for(Result i: results) {
            list.add(valueOf(i));
        }
        return list;
    }

    public Result mapTo() {
        return new Result(id,result,test,user_id);
    }
}
