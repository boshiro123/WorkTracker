package com.example.demo.repository;

import com.example.demo.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM public.answer WHERE id=?;", nativeQuery = true)
    void DeleteById(Long id);
}
