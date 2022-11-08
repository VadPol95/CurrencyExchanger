package com.vadpol.ex.dto;

import com.vadpol.ex.entity.CurrencyType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeDto {
    private String phoneNumber;
    private CurrencyType currency;
    private BigDecimal amount;
}
