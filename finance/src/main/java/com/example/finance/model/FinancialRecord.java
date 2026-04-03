package com.example.finance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "financial_records")
public class FinancialRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "Amount must be greater than 0")
    private double amount;

    @NotNull(message = "Type cannot be null")
    @Enumerated(EnumType.STRING)
    private RecordType type;

    @NotNull(message = "Category cannot be null")
    private String category;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    private String notes;

    // Constructors
    public FinancialRecord() {}

    public FinancialRecord(double amount, RecordType type, String category, LocalDate date, String notes) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public RecordType getType() { return type; }
    public void setType(RecordType type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}