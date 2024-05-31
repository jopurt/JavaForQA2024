package ru.shop.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
// Сущности храниться в БД
@Entity
// Задаём таблицы для хранения
@Table(name = "product_return")

public class ProductReturn {
    @Id
    UUID id;
    UUID orderId;
    LocalDate date;
    int quantity;
}
