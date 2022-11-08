package com.vadpol.ex.service;

import com.vadpol.ex.entity.CurrencyType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ExchangeService {

    void exchange(String phoneNumber, CurrencyType currency, BigDecimal amount);
}
