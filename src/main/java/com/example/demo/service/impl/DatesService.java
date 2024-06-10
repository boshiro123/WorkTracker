package com.example.demo.service.impl;

import com.example.demo.dto.DatesDto;
import com.example.demo.dto.ResultDto;
import com.example.demo.dto.TasksDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.*;
import com.example.demo.exception.StatusNotFoundException;
import com.example.demo.exception.TestNotFoundException;
import com.example.demo.repository.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DatesService {
    private final DatesRepository datesRepository;
    private final TasksRepository tasksRepository;
    private final UserService userService;
    @Autowired
    public DatesService(DatesRepository datesRepository, TasksRepository tasksRepository, UserService userService) {
        this.datesRepository = datesRepository;
        this.tasksRepository = tasksRepository;
        this.userService = userService;
    }


    public DatesDto checkTime(LocalDate date, Long user_id){
        DatesDto check;
        try {
             check = DatesDto.valueOf(datesRepository.findByDate(date, user_id));
        }catch (NullPointerException e){
            System.out.println("Дата не найдена");
            return new DatesDto();
        }
        return check;
    }

    public boolean save(LocalDate date, Time startTime, Long user_id){
            Dates find = datesRepository.findByDate(date,user_id);
            if (find==null){
            System.out.println("Дата не найдена -> добавляем в бд");
            Dates newDate = new Dates(date,startTime,user_id);
            datesRepository.save(newDate);
            return  true;
            }
            else{
                System.out.println(find);
                System.out.println("Дата была найдена");
            }
            return false;
    }

    public boolean update(LocalDate date, Time endTime, Long user_id){
        Dates find = datesRepository.findByDate(date, user_id);
        if (find!=null && find.getEndTime()==null){
            System.out.println("Дата найдена и endTime != null -> делаем update");
            find.setEndTime(endTime);
            datesRepository.save(find);
            return true;
        }
        System.out.println("Что-то пошло не так");
        return false;
    }

    public int getLastMonth(Long id){
        List<DatesDto> dates = DatesDto.ListValueOf(datesRepository.findByLastMonth(getCurrentMonthFirstDay(),id));
        return dates.size();
    }
    public String getAnaliz(Long id, Boolean isReport){
        String analiz = "";
        int count = 0, sum = 0;
        LocalDate currentDate = null;
        List<DatesDto> datesList = DatesDto.ListValueOf(datesRepository.findAllByUser_id(id));
        List<TasksDto> tasksList = TasksDto.ListValueOf(tasksRepository.findAllByUser_idOrderByDate(id));
        long workTime = datesList.size()*6L; //Рабочее время равно все дни когда работал умноженное на 6 часов
        long totalDifference = 0; // переменная для суммирования разницы времени

        for (DatesDto dates : datesList) {
            Time startTime = dates.getStartTime();
            Time endTime = dates.getEndTime();

            // Вычисляем разницу в миллисекундах между startTime и endTime
            long difference = endTime.getTime() - startTime.getTime();

            // Суммируем разницу с общей переменной
            totalDifference += difference;
        }

// Выводим общую разницу времени в нужном формате
        long seconds = totalDifference / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        analiz+="Должен был работать за всё время: " + workTime +" часов \n";
        analiz += "Общее временя работы: " + hours + " ч " + (minutes % 60) + " мин " + (seconds % 60) + " сек \n";
        for (TasksDto task : tasksList) {
            if (!task.getDate().equals(currentDate)) {
                // Дата сменилась, выводим количество выполненных задач для предыдущей даты
                if (currentDate != null) {
//                    System.out.println("Дата: " + currentDate + ", Количество выполненных задач: " + count);
                    sum+=count;
                }

                // Сбрасываем счетчик и обновляем текущую дату
                count = 0;
                currentDate = task.getDate();
            }

            if ("Выполнено".equals(task.getStatus())) {
                // Увеличиваем счетчик выполненных задач
                count++;
            }
        }
        // Вывод результатов для последней даты
        if (currentDate != null) {
//            System.out.println("Дата: " + currentDate + ", Количество выполненных задач: " + count);
            sum+=count;
        }
//        System.out.println((double) tasksList.size()/sum);
        analiz+="Выполнено " + sum + " задач из " + tasksList.size() +".\n";

        if (hours < workTime){
            analiz+="Из всех дней когда вы заходили в систему вы не проработали необходимое количество часов.\n";
        }
        else if(hours > workTime) {
            analiz += "Вы молодец! Вы проработали все необходимые часы. \n";
        }
        if ((double) sum/tasksList.size()>=0.75){
            analiz+="Так же выполнена большая часть задач. \n";
        }else{
            analiz+="К сожалению сделано мало задач. \n";

        }

        System.out.println(analiz);
        if(isReport){
            return analiz;
        }else {
            JSONObject result = new JSONObject();
            result.put("result", analiz);
// Преобразуем объект в JSON строку
            String jsonResult = result.toString();
            System.out.println(jsonResult);
            return jsonResult;
        }
    }

    public void createReport(Long id) throws IOException, InvalidFormatException {
        UserDto user = userService.fingById(id);
        String text = "ФИО: " + user.getName()+"\n"+
                "Почта: " + user.getEmail()+"\n"+
                "Статус: " + user.getStatus().getName()+"\n"+
                "Опыт: " + user.getUser_info().getExperience()+"\n"+
                "Город: " + user.getUser_info().getCity()+"\n"+
                "Должность: " + user.getUser_info().getPosition()+"\n"+
                "Пол: " + user.getUser_info().getMale()+"\n"+
                "Ставка: " + user.getUser_info().getBet()+"\n\n";
        text += getAnaliz(id,true);
        report(text);
    }

    public void report(String text) {
        String[] lines = text.split("\n");
        try {
            XWPFDocument document = new XWPFDocument();
            FileOutputStream out = new FileOutputStream(new File("report.docx"));
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setFontSize(14);
            run.setFontFamily("Times New Roman");
            for (String line : lines) {
                run.setText(line);
                run.addBreak();
            }
            document.write(out);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Document created");
    }

    private static LocalDate getCurrentMonthFirstDay() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        return LocalDate.of(year, month, 1);
    }

    public List<String> getWorkHours(Long id){
        List<DatesDto> List = DatesDto.ListValueOf(datesRepository.findAllByUser_id(id));
        List<String> hours = new ArrayList<>();
        for (DatesDto i: List){
            long difference = (i.getEndTime().getTime()-i.getStartTime().getTime())/(60*60*1000);
            hours.add(String.valueOf(difference));
        }
        System.out.println(hours.toString());
        return hours;
    }
    public List<String> getWorkDates(Long id){
        List<DatesDto> List = DatesDto.ListValueOf(datesRepository.findAllByUser_id(id));
        List<String> hours = new ArrayList<>();
        for (DatesDto i: List){
            hours.add(i.getDate().toString());
        }
        return hours;
    }

}
//При нажатии на календарь выводило время если есть и блокировало кнопки
//Доделать Окончание работы
//Проверить по пользователям
