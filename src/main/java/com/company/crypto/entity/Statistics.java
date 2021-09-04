package com.company.crypto.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "statistics")
public class Statistics extends BaseEntity{

    private Double pl;

    private String symbol;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
}
