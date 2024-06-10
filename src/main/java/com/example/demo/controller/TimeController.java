package com.example.demo.controller;

import com.example.demo.dto.DatesDto;
import com.example.demo.dto.ResultDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Dates;
import com.example.demo.service.impl.DatesService;
import com.example.demo.service.impl.ResultService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v2/time")
public class TimeController {
    private final DatesService datesService;

    @Autowired
    public TimeController(DatesService datesService) {
        this.datesService = datesService;
    }

    @GetMapping("/checkTime")
    @ResponseStatus(HttpStatus.OK)
    public DatesDto checkTime(@RequestParam("Date") String stringDate,
                          @RequestParam("user_id")Long id)  {
        System.out.println("Date: " +parseDateString(stringDate));
        System.out.println("User id: " + id);
        return datesService.checkTime(parseDateString(stringDate),id);
    }


    @PostMapping("/addTime")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean saveTime(@RequestParam("Date") String stringDate,
                         @RequestParam("StartTime")Time start,
                         @RequestParam("user_id")Long id) {
        LocalDate date = parseDateString(stringDate);
        return datesService.save(date,start,id);
    }
    @PutMapping("/updateTime")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateUser(@RequestParam("Date") String stringDate,
                           @RequestParam("EndTime")Time end,
                           @RequestParam("user_id")Long id ) {
                System.out.println("Date: " + stringDate);
        System.out.println("Start: " + end);
        return datesService.update(parseDateString(stringDate),end,id);
    }
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getAnaliz(@PathVariable Long id){
        return datesService.getAnaliz(id,false);
    }
    @GetMapping("getWorkHours/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getWorkHours(@PathVariable Long id){
        return datesService.getWorkHours(id);
    }
    @GetMapping("getWorkDates/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getWorkDates(@PathVariable Long id){
        return datesService.getWorkDates(id);
    }
    @GetMapping("/workDays/{id}")
    @ResponseStatus(HttpStatus.OK)
    public int getWorkDays(@PathVariable Long id){
        return datesService.getLastMonth(id);
    }

    @GetMapping("/createReport/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void createReport(@PathVariable Long id) throws IOException, InvalidFormatException {
        datesService.createReport(id);
    }

    public static LocalDate parseDateString(String dateString) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate parsedDate = LocalDate.parse(dateString, inputFormatter);
            LocalDate incrementedDate = parsedDate.plusDays(1);

            String formattedDate = outputFormatter.format(incrementedDate);

            return LocalDate.parse(formattedDate, outputFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
