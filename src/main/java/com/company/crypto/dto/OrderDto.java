package com.company.crypto.dto;

import com.company.crypto.enums.Action;
import com.company.crypto.enums.Symbols;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDto extends BaseDto {

    @NotNull
    private Symbols symbol;

    @NotNull
    private Action order;

    private Double usdt;

    private Double amount;

}
