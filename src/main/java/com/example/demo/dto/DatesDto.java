package com.example.demo.dto;

import com.example.demo.entity.Dates;
import com.example.demo.entity.Tasks;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatesDto {
    private Long id;
    private LocalDate date;
    private Time startTime;
    private Time endTime;
    private Long user_id;

    public static DatesDto valueOf(Dates date) {
        return new DatesDto(
                date.getId(),
                date.getDate(),
                date.getStartTime(),
                date.getEndTime(),
                date.getUser_id()
        );
    }

    public Dates mapTo() {
        return new Dates(id,date,startTime,endTime,user_id);
    }
    public static List<DatesDto> ListValueOf(List<Dates> dates) {
        List<DatesDto> list = new ArrayList<>();
        for(Dates i: dates) {
            list.add(valueOf(i));
        }
        return list;
    }
}
