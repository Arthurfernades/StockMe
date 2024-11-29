package com.example.stockapi.model;

import com.example.stockapi.model.enums.ETypeStock;
import com.example.stockapi.model.enums.ETypeTransaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private String name;

    private String code;

    @Enumerated(EnumType.STRING)
    private ETypeStock stockType;

    @Enumerated(EnumType.STRING)
    private ETypeTransaction transactionType;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date lastTransactionDate;

    private double amountValue;

    private Long userId;

}
