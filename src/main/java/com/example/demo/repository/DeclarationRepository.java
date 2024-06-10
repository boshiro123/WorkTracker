package com.example.demo.repository;

import com.example.demo.entity.Declaration;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DeclarationRepository extends JpaRepository<Declaration, Long> {
    @Query(value = "SELECT * from public.declaration WHERE user_id = ?;", nativeQuery = true)
    List<Declaration> findByUser_id(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE public.declaration SET status = ? WHERE id=?;", nativeQuery = true)
    void updateStatus(String status, Long id);
}
