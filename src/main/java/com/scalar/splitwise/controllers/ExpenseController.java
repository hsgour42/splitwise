package com.scalar.splitwise.controllers;

import com.scalar.splitwise.dtos.SettleUpGroupRequestDto;
import com.scalar.splitwise.dtos.SettleUpGroupResponseDto;
import com.scalar.splitwise.dtos.SettleUpUserRequestDto;
import com.scalar.splitwise.dtos.SettleUpUserResponseDto;
import com.scalar.splitwise.exceptions.GroupNotFoundException;
import com.scalar.splitwise.services.ExpenseService;
import com.scalar.splitwise.strategies.settleupstrategy.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ExpenseController {
    private ExpenseService expenseService;
    @Autowired
    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }
    public SettleUpUserResponseDto settleUpUser(SettleUpUserRequestDto request){
        Long userId = request.getUserId();
        List<Transaction> transactions = expenseService
                .settleUpUser(userId);
        SettleUpUserResponseDto response = new SettleUpUserResponseDto();
        response.setStatus("SUCCESS");
        response.setTransactions(transactions);
        return response;
    }

    public SettleUpGroupResponseDto settleUpGroup(SettleUpGroupRequestDto request) throws GroupNotFoundException {
        Long groupId = request.getGroupId();
        List<Transaction> transactions = expenseService.settleUpGroup(request.getGroupId());
        return null;
    }
}
