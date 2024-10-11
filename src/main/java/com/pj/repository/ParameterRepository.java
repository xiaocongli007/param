package com.pj.repository;

import com.pj.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    Optional<Parameter> findByKey(String key);
}