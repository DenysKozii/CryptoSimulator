package com.company.crypto.repository;

import com.company.crypto.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, PagingAndSortingRepository<Question, Long> {

    Optional<Question> findByOrderId(Long orderId);

    Optional<Question> findByTitle(String title);
}
