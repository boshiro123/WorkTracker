package com.example.demo.repository;

import com.example.demo.entity.Tasks;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks,Long> {
    @Query(value = "SELECT * from public.tasks WHERE user_id = ? and date = ?;", nativeQuery = true)
    List<Tasks> findAllTasks(Long id, LocalDate date);

    @Query(value = "SELECT * from public.tasks WHERE user_id = ?;", nativeQuery = true)
    List<Tasks> findAllByUser_id(Long id);

    @Query(value = "SELECT * from public.tasks WHERE user_id = ? order by date;", nativeQuery = true)
    List<Tasks> findAllByUser_idOrderByDate(Long id);


    @Query(value = "SELECT DISTINCT date FROM public.tasks WHERE user_id = ? order by date;", nativeQuery = true)
    List<Date> findAllDatesFromUser(Long id);
}
