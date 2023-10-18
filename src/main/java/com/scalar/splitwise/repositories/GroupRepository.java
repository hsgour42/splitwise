package com.scalar.splitwise.repositories;

import com.scalar.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findById(Group groupId);
}
