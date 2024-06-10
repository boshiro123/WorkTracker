package com.example.demo.dto;

import com.example.demo.entity.Declaration;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationDto {
    private Long id;
    private Long userId;
    private String fio;
    private String text;
    private LocalDate from;
    private LocalDate to;
    private LocalDate date;
    private String status;

    public DeclarationDto(Long userId, String fio, String text, LocalDate from, LocalDate to, LocalDate date, String status) {
        this.userId = userId;
        this.fio = fio;
        this.text = text;
        this.from = from;
        this.to = to;
        this.date = date;
        this.status = status;
    }

    public static DeclarationDto valueOf(Declaration declaration) {
        return new DeclarationDto(
                declaration.getId(),
                declaration.getUserId(),
                declaration.getFio(),
                declaration.getText(),
                declaration.getFrom(),
                declaration.getTo(),
                declaration.getDate(),
                declaration.getStatus()
        );
    }
    public static List<DeclarationDto> ListValueOf(List<Declaration> declarations) {
        List<DeclarationDto> list = new ArrayList<>();
        for(Declaration i: declarations) {
            list.add(valueOf(i));
        }
        return list;
    }
    public Declaration mapTo() {
        return new Declaration(id,userId,fio,text,from,to,date,status);
    }

}
