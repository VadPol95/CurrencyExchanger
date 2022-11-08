package com.vadpol.ex.dto;

import com.vadpol.ex.entity.CurrencyType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class WalletDto {
    private Long id;
    private CurrencyType currency;
    private Timestamp lastUpdate;
    private BigDecimal ammount;
}
