package com.scalar.splitwise.strategies.settleupstrategy;

import com.scalar.splitwise.models.Expense;
import com.scalar.splitwise.models.User;
import com.scalar.splitwise.models.UserExpense;
import com.scalar.splitwise.models.UserExpenseType;
import com.scalar.splitwise.repositories.UserExpenseRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;


@Component("twoSetsSettleUpStrategy")
public class TwoSetsSettleUpStrategy implements SettleUpStrategy{
    private UserExpenseRepository userExpenseRepository;
    @Autowired
    public TwoSetsSettleUpStrategy(UserExpenseRepository userExpenseRepository){
        this.userExpenseRepository=userExpenseRepository;
    }
    @Override
    public List<Transaction> settle(
            List<Expense> expenses
    ) {
        //for all expenses that I have settle,who pay how much and who had to pay how much
        List<UserExpense> allUserExpenseForTheseExpenses = userExpenseRepository.findAllByExpenseIn(expenses);
        HashMap<User,Integer> moneyExtraPaid = new HashMap<>();

        //I want through all the expenses and found out who has paid how much extra or less
        for(UserExpense userExpense : allUserExpenseForTheseExpenses){
            User user = userExpense.getUser();
            int currentExtraPaid = 0;

            if(moneyExtraPaid.containsKey(user)){
                currentExtraPaid = moneyExtraPaid.get(user);
            }

            if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)){
                moneyExtraPaid.put(user,currentExtraPaid + userExpense.getAmount() );
            }else{
                moneyExtraPaid.put(user,currentExtraPaid - userExpense.getAmount());
            }
        }

        TreeSet<Pair<User, Integer>> extraPaid = new TreeSet<>();
        TreeSet<Pair<User, Integer>> lessPaid = new TreeSet<>();

        for(Map.Entry<User,Integer> userAmount: moneyExtraPaid.entrySet()){
            if(userAmount.getValue() < 0){
                lessPaid.add(new Pair<>(userAmount.getKey(),userAmount.getValue()));
            }else{
                extraPaid.add(new Pair<>(userAmount.getKey(),userAmount.getValue()));
            }
        }


        List<Transaction> transactions = new ArrayList<>();
        while(!lessPaid.isEmpty()){
            Pair<User,Integer> lessPaidPair = lessPaid.pollFirst();
            Pair<User,Integer> extraPaidPair = extraPaid.pollFirst();

            Transaction t = new Transaction();
            t.setFrom(lessPaidPair.a);
            t.setTo(extraPaidPair.a);
            if(Math.abs(lessPaidPair.b) < extraPaidPair.b){  //vikram -100 : deepak +200
                t.setAmount(Math.abs(lessPaidPair.b));
                int amount = extraPaidPair.b - Math.abs(lessPaidPair.b);
                if(amount != 0){
                    extraPaid.add(new Pair<>(extraPaidPair.a , amount));
                }
            } else{ //vikram -200 : deepak +100
                t.setAmount(Math.abs(extraPaidPair.b));
                if(lessPaidPair.b + extraPaidPair.b != 0){
                    lessPaid.add(new Pair<>(lessPaidPair.a , lessPaidPair.b + extraPaidPair.b ));
                }
            }

            transactions.add(t);
        }
        return transactions;
    }


}
