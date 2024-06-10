package com.example.demo.service.impl;

import com.example.demo.dto.DeclarationDto;
import com.example.demo.dto.TasksDto;
import com.example.demo.entity.Declaration;
import com.example.demo.entity.Tasks;
import com.example.demo.repository.DeclarationRepository;
import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
@Service
public class DeclarationService {
    private DeclarationRepository declarationRepository;

    @Autowired
    public DeclarationService(DeclarationRepository declarationRepository) {
        this.declarationRepository = declarationRepository;
    }

    public void add(String text, String fio, Long id, LocalDate date){
        log.info("Method add declaration:-----------");
        Declaration dec = new Declaration(id,fio,text,extractDates(text)[0],extractDates(text)[1],date,"on_admin_review");
        System.out.println(dec.toString());
        declarationRepository.save(dec);
    }
    public List<DeclarationDto> getDeclarations(Long id){
        log.info("Method getDeclarations:-----------");
        return DeclarationDto.ListValueOf(declarationRepository.findByUser_id(id));
    }
    public void access(Long id){
        log.info("Method access:-----------");
        declarationRepository.updateStatus("accept",id);
    }
    public void refuse(Long id){
        log.info("Method refuse:-----------");
        declarationRepository.updateStatus("refuse",id);
    }

    private static LocalDate[] extractDates(String text) {
        List<LocalDate> dateList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\b(\\d{2}\\.\\d{2}\\.\\d{4})\\b");
        Matcher matcher = pattern.matcher(text);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        while (matcher.find()) {
            String dateString = matcher.group(1);
            LocalDate date = LocalDate.parse(dateString, formatter);
            dateList.add(date);
        }

        LocalDate[] dates = new LocalDate[dateList.size()];
        return dateList.toArray(dates);
    }

}
