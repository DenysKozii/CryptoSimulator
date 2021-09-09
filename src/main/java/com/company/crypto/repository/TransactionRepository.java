package com.company.crypto.repository;

import com.company.crypto.entity.Transaction;
import com.company.crypto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, PagingAndSortingRepository<Transaction, Long> {
    List<Transaction> findAllByUserAndSymbolAndAnalysedFalse(User user, String symbol);

    List<Transaction> findAllByUser(User user);
}
