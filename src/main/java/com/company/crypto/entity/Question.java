package com.company.crypto.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "questions")
public class Question extends BaseEntity{

    @NonNull
    private Long orderId;

    @NonNull
    private String title;

    @Column(nullable = false)
    @Type(type = "text")
    protected String context;

    @NonNull
    private Double answer;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imageQuestion;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imageAnswer;

}
