package com.company.crypto.repository;

import com.company.crypto.entity.Asset;
import com.company.crypto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long>, PagingAndSortingRepository<Asset, Long> {
    Optional<Asset> findFirstByUserAndSymbol(User user, String symbol);

    List<Asset> findByUser(User user);
}
