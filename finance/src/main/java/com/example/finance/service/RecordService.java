package com.example.finance.service;

import com.example.finance.model.FinancialRecord;
import com.example.finance.model.Role;
import com.example.finance.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RecordService {
    @Autowired
    private RecordRepository recordRepository;

    // Create Record (only ADMIN allowed)
    public FinancialRecord createRecord(FinancialRecord record, Role role) {
        if (role == Role.VIEWER) {
            throw new RuntimeException("Access Denied");
        }
        return recordRepository.save(record);
    }

    // Get All Records
    public List<FinancialRecord> getAllRecords() {
        return recordRepository.findAll();
    }

    // Delete Record (only ADMIN)
    public void deleteRecord(Long id, Role role) {
        if (role != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can delete");
        }
        recordRepository.deleteById(id);
    }

    public FinancialRecord updateRecord(Long id, FinancialRecord updatedRecord, Role role) {

        if (role == Role.VIEWER) {
            throw new RuntimeException("Access Denied");
        }

        FinancialRecord record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setAmount(updatedRecord.getAmount());
        record.setType(updatedRecord.getType());
        record.setCategory(updatedRecord.getCategory());
        record.setDate(updatedRecord.getDate());
        record.setNotes(updatedRecord.getNotes());

        return recordRepository.save(record);
    }
}
