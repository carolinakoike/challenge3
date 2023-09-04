package com.compass.challenge3.repository;

import com.compass.challenge3.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}