package com.scalar.splitwise.strategies.settleupstrategy;

import com.scalar.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction{
    private User from;
    private User to;
    private Integer amount;

}
