package com.example.demo.repository;

import com.example.demo.entity.Dates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DatesRepository extends JpaRepository<Dates,Long> {
    @Query(value = "SELECT * FROM public.dates WHERE date = ? and user_id = ?;", nativeQuery = true)
    Dates findByDate(LocalDate date, Long id);

    @Query(value = "SELECT * FROM public.dates WHERE user_id = ?;", nativeQuery = true)
    List<Dates> findAllByUser_id( Long id);

    @Query(value = "SELECT * FROM public.dates WHERE date >=? AND user_id = ?;", nativeQuery = true)
    List<Dates> findByLastMonth(LocalDate date, Long id);
}
