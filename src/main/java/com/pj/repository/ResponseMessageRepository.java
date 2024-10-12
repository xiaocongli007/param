package com.pj.repository;

import com.pj.model.ResponseMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponseMessageRepository extends JpaRepository<ResponseMessage, Long> {
    Optional<ResponseMessage> findByCode(String code);
}