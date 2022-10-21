package com.vadpol.ex.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TransferDto {
    long id;
    BigDecimal amount;
}
