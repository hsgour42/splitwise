package com.scalar.splitwise.repositories;

import com.scalar.splitwise.models.Expense;
import com.scalar.splitwise.models.Group;
import com.scalar.splitwise.models.User;
import com.scalar.splitwise.models.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense,Long> {
    List<UserExpense> findAllByUser(User user);
    List<UserExpense> findAllByExpenseIn(List<Expense> expenses);
}

