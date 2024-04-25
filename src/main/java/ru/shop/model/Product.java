package ru.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
// Задаём таблицы для хранения
@Table
public class Product {
    @Id
    private UUID id;
    private String name;
    long cost;
    // ENUM храниться в БД как строка
    @Enumerated(EnumType.STRING)
    private ProductType productType;
}
