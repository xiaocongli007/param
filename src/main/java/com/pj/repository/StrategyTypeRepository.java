package com.pj.repository;

import com.pj.model.StrategyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StrategyTypeRepository extends JpaRepository<StrategyType, Long> {
    Optional<StrategyType> findByStrategyCode(String strategyCode);
}