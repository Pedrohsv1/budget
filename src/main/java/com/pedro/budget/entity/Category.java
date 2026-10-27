package com.pedro.budget.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedro.budget.enums.CategoryType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotEmpty(message = "Title cannot be blank")
    String title;

    @NotNull(message = "Type cannot be null")
    CategoryType type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    User user;

    public Category(String title, CategoryType type, User user) {
        this.title = title;
        this.type = type;
        this.user = user;
    }

    public void update(String title, CategoryType type) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (type != null) {
            this.type = type;
        }
    }
}
