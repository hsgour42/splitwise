package com.scalar.splitwise.strategies.settleupstrategy;

import com.scalar.splitwise.models.Expense;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settle(List<Expense> expenses);
}
