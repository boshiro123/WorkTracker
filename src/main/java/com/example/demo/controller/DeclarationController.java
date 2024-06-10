package com.example.demo.controller;

import com.example.demo.dto.DatesDto;
import com.example.demo.dto.DeclarationDto;
import com.example.demo.entity.Declaration;
import com.example.demo.service.impl.DatesService;
import com.example.demo.service.impl.DeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v2/declaration")
public class DeclarationController {
    private DeclarationService declarationService;

    @Autowired
    public DeclarationController(DeclarationService declarationService) {
        this.declarationService = declarationService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestParam("text") String text,
                    @RequestParam("fio")String fio,
                    @RequestParam("id")Long id,
                    @RequestParam("date")String stringDate) {
        LocalDate date = parseDateString(stringDate);
        System.out.println("\n------Add new Declaration------");
        System.out.println("text: " + text +"\n"+
                            "fio: " + fio + "\n" +
                            "id: " + id + "\n" +
                            "date: " + date);
        declarationService.add(text,fio,id,date);
    }
    @GetMapping("/getDeclarations/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<DeclarationDto> getDeclarations(@PathVariable Long id) {
        System.out.println("\n------getDeclarations------");
        System.out.println("userId: " + id +"\n");
        return declarationService.getDeclarations(id);
    }

    @PutMapping("/access/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void refuse(@PathVariable Long id) {
        System.out.println("\n------refuse------");
        System.out.println("declaration: " + id +"\n");
        declarationService.access(id);
    }

    @PutMapping("/refuse/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void access(@PathVariable Long id) {
        System.out.println("\n------access------");
        System.out.println("declaration: " + id +"\n");
        declarationService.refuse(id);
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
