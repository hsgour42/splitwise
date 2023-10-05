package com.scalar.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Group extends BaseModel{
    private String name;
    private String descriptions;

    @ManyToMany
    private List<User> members;

    @ManyToOne
    private  User createdBy;
}
