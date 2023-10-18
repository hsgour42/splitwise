package com.scalar.splitwise.repositories;

import com.scalar.splitwise.models.Expense;
import com.scalar.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense , Long> {
    @Override
    Optional<Expense> findById(Long groupId);

    List<Expense> findAllByGroup(Group group);
}
