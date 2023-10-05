package com.scalar.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Expense extends  BaseModel{
    private int amount;
    private String description;

    @Enumerated(EnumType.ORDINAL)   //Set index wise value of enum
    private ExpenseType expenseType;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private Group group;
}
