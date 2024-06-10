package com.example.demo.repository;

import com.example.demo.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ResultRepository extends JpaRepository<Result,Long> {
    @Query(value = "SELECT * FROM public.result WHERE user_id=? and test_id=?;", nativeQuery = true)
    Result findByUser_id(Long id, Long id2);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE public.result SET result = ? WHERE user_id=? and test_id = ?;", nativeQuery = true)//Исправить
    void updateByUser_id(Double result, Long user_id, Long test_id);
    @Query(value = "SELECT * FROM public.result WHERE user_id=?;", nativeQuery = true)
    List<Result> findAllByUser_id(Long id);

    @Query(value = "SELECT AVG(result) FROM public.result WHERE test_id=?;", nativeQuery = true)
    Double averageScore(Long id);

}
