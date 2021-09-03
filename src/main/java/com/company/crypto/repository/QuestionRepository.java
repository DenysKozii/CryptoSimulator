package com.company.crypto.repository;

import com.company.crypto.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, PagingAndSortingRepository<Question, Long> {

}
