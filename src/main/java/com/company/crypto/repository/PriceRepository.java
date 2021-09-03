package com.company.crypto.repository;

import com.company.crypto.entity.Price;
import com.company.crypto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long>, PagingAndSortingRepository<Price, Long> {

    Optional<Price> findBySymbol(String symbol);

}
