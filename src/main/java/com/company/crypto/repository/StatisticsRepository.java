package com.company.crypto.repository;

import com.company.crypto.entity.Statistics;
import com.company.crypto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long>, PagingAndSortingRepository<Statistics, Long> {

    List<Statistics> findAllByUser(User user);

}
