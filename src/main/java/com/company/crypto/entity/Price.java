package com.company.crypto.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "prices")
public class Price extends BaseEntity {
    @NonNull
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Must not be empty")
    private String symbol;

    private Double price;
}
