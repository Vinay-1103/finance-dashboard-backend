package com.example.finance.repository;

import com.example.finance.model.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<FinancialRecord, Long>{
}
