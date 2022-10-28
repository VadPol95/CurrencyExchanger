package com.vadpol.ex.service;

import com.vadpol.ex.entity.CurrencyEnum;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ExchangeService {

    void exchange(String phoneNumber, CurrencyEnum currency, BigDecimal amount);
}
