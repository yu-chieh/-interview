package com.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.user.entity.ExchangeRateEntity;

@Repository
@Transactional(rollbackFor=Exception.class)
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, String> {
}