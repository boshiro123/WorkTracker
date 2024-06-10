package com.example.demo.repository;

import com.example.demo.dto.QuestionDto;
import com.example.demo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> , JpaSpecificationExecutor<Question> {

    @Query(value = "SELECT * FROM public.question WHERE test_id=?;", nativeQuery = true)
    List<Question> findAllById_test(Long id);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM public.question WHERE test_id=?;", nativeQuery = true)
    void deleteById_test(Long id);

}
