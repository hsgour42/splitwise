package com.scalar.splitwise.strategies.settleupstrategy;

import com.scalar.splitwise.models.Expense;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bruteForceSettleUpStrategy")
public class BruteForceSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settle(List<Expense> expenses) {
        return null;
    }
}
