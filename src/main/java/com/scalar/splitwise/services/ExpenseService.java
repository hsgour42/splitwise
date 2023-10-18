package com.scalar.splitwise.services;

import com.scalar.splitwise.exceptions.GroupNotFoundException;
import com.scalar.splitwise.models.Expense;
import com.scalar.splitwise.models.Group;
import com.scalar.splitwise.models.User;
import com.scalar.splitwise.models.UserExpense;
import com.scalar.splitwise.repositories.ExpenseRepository;
import com.scalar.splitwise.repositories.GroupRepository;
import com.scalar.splitwise.repositories.UserExpenseRepository;
import com.scalar.splitwise.repositories.UserRepository;
import com.scalar.splitwise.strategies.settleupstrategy.SettleUpStrategy;
import com.scalar.splitwise.strategies.settleupstrategy.Transaction;
import com.scalar.splitwise.strategies.settleupstrategy.TwoSetsSettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private UserRepository userRepository;
    private UserExpenseRepository userExpenseRepository;
    private SettleUpStrategy settleUpStrategy;
    private ExpenseRepository expenseRepository;
    private GroupRepository groupRepository;
    @Autowired
    public ExpenseService(UserRepository userRepository,
                          UserExpenseRepository userExpenseRepository,
                          @Qualifier("twoSetsSettleUpStrategy") SettleUpStrategy settleUpStrategy,
                          ExpenseRepository expenseRepository,
                          GroupRepository groupRepository

                          ){
        this.userRepository = userRepository;
        this.userExpenseRepository = userExpenseRepository;
        this.settleUpStrategy = settleUpStrategy;
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
    }
    public List<Transaction> settleUpUser(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            //throw exception
        }
        List<UserExpense> userExpenses = userExpenseRepository.findAllByUser(userOptional.get());
        List<Expense> expensesInvolvingUser = new ArrayList<>();
        for(UserExpense userExpense : userExpenses){
            expensesInvolvingUser.add(userExpense.getExpense());
        }
        List<Transaction> transactions = settleUpStrategy.settle(expensesInvolvingUser);

        List<Transaction> filteredTransaction = new ArrayList<>();

        //filter current user transaction only
        for(Transaction transaction: transactions){
            if(transaction.getTo().equals(userOptional.get()) ||
                transaction.getFrom().equals(userOptional.get()))
            {
                filteredTransaction.add(transaction);
            }
        }
        return filteredTransaction;
    }

    public List<Transaction> settleUpGroup(Long groupId) throws GroupNotFoundException {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Group group;
        if(groupOptional.isEmpty()){
            throw new GroupNotFoundException("Group not found");
        }

        List<Expense> expenses = expenseRepository.findAllByGroup(groupOptional.get());
        List<Transaction> transactions = settleUpStrategy.settle(expenses);

        return transactions;
    }
}
