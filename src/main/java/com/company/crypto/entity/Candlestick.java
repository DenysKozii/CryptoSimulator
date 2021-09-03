package com.company.crypto.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "candlesticks")
public class Candlestick extends BaseEntity {
    private String symbol;

    private Double close;

    @Column(name = "date", nullable = false)
    private Date date;
}
