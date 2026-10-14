package com.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
        SELECT m FROM Message m
        LEFT JOIN FETCH m.user u
        LEFT JOIN FETCH u.address
        LEFT JOIN FETCH m.category
        ORDER BY m.id DESC
    """)
    List<Message> findAllWithRelations();

    @Query("""
        SELECT m FROM Message m
        LEFT JOIN FETCH m.user u
        LEFT JOIN FETCH u.address
        LEFT JOIN FETCH m.category
        WHERE m.id = :id
    """)
    Optional<Message> findByIdWithRelations(@Param("id") Long id);
}