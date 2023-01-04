package com.example.demo.database;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
@Getter
public class CategoryEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer parentCategoryId;

}
