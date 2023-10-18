package com.scalar.splitwise.dtos;

import com.scalar.splitwise.strategies.settleupstrategy.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettleUpUserResponseDto {
    private String message;
    private String status;
    private List<Transaction> transactions;
}
