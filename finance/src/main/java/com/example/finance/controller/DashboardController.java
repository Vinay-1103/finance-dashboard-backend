package com.example.finance.controller;


import com.example.finance.model.FinancialRecord;
import com.example.finance.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")

public class DashboardController {
    @Autowired
    private RecordRepository recordRepository;

    @GetMapping("/summary")
    public String getSummary() {

        List<FinancialRecord> records = recordRepository.findAll();

        double totalIncome = records.stream()
                .filter(r -> r.getType().toString().equals("INCOME"))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double totalExpense = records.stream()
                .filter(r -> r.getType().toString().equals("EXPENSE"))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double netBalance = totalIncome - totalExpense;

        return "Income: " + totalIncome +
                ", Expense: " + totalExpense +
                ", Balance: " + netBalance;
    }
}
