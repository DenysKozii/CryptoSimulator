package com.company.crypto.entity;

import com.company.crypto.enums.Action;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    private String symbol;

    private Double usdt;

    private Double amount;

    private Double price;

    private Action action;

    private boolean analysed;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
}
