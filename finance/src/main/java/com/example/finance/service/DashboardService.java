package com.example.finance.service;

import com.example.finance.model.FinancialRecord;
import com.example.finance.model.RecordType;
import com.example.finance.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private RecordRepository recordRepository;

    public Map<String, Double> getSummary() {

        List<FinancialRecord> records = recordRepository.findAll();

        double totalIncome = records.stream()
                .filter(r -> r.getType() == RecordType.INCOME)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double totalExpense = records.stream()
                .filter(r -> r.getType() == RecordType.EXPENSE)
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double netBalance = totalIncome - totalExpense;

        Map<String, Double> response = new HashMap<>();
        response.put("totalIncome", totalIncome);
        response.put("totalExpense", totalExpense);
        response.put("netBalance", netBalance);

        return response;
    }
}