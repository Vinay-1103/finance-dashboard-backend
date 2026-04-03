package com.example.finance.controller;

import com.example.finance.model.FinancialRecord;
import com.example.finance.model.Role;
import com.example.finance.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    // Create Record
    @PostMapping
    public FinancialRecord createRecord(@Valid @RequestBody FinancialRecord record,
                                        @RequestParam Role role) {
        return recordService.createRecord(record, role);
    }

    // Get All Records
    @GetMapping
    public List<FinancialRecord> getAllRecords() {
        return recordService.getAllRecords();
    }

    // Update Record
    @PutMapping("/{id}")
    public FinancialRecord updateRecord(@PathVariable Long id,
                                        @Valid @RequestBody FinancialRecord record,
                                        @RequestParam Role role) {
        return recordService.updateRecord(id, record, role);
    }

    // Delete Record
    @DeleteMapping("/{id}")
    public String deleteRecord(@PathVariable Long id,
                               @RequestParam Role role) {
        recordService.deleteRecord(id, role);
        return "Deleted Successfully";
    }
}