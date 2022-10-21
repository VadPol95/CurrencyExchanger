package com.vadpol.ex.dto;

import com.vadpol.ex.entity.CurrencyEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class WalletDto {
    private Long id;
    private CurrencyEnum currency;
    private Timestamp lastUpdate;
    private BigDecimal ammount;
}
