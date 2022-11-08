package com.vadpol.ex.service.impl;

import com.vadpol.ex.entity.*;
import com.vadpol.ex.exceptions.NotEnoughtMoneyException;
import com.vadpol.ex.repository.WalletRepository;
import com.vadpol.ex.service.ExchangeService;
import com.vadpol.ex.service.RateService;
import com.vadpol.ex.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final WalletService walletService;
    private final RateService rateService;
    private final WalletRepository walletRepository;



    @Override
    public void exchange(String phoneNumber, CurrencyType currency, BigDecimal amount) {
        List<Wallet> walletList = walletService.getWalletsByPhoneNumber(phoneNumber);

        Wallet wUAH = walletList.stream().filter(w -> w.getCurrency().equals(CurrencyType.UAH)).findFirst().get();
        Wallet wUSD = walletList.stream().filter(w -> w.getCurrency().equals(CurrencyType.USD)).findFirst().get();

        Rate rate = rateService.getCurrentRate();


        if (CurrencyType.UAH.equals(currency)) {
            BigDecimal valueUAH = amount.divide(rate.getBuy(), RoundingMode.HALF_UP);
            if (wUSD.getAmmount().compareTo(valueUAH) < 0) {
                throw new NotEnoughtMoneyException();
            }
            wUAH.setAmmount(wUAH.getAmmount().add(amount));
            wUSD.setAmmount(wUSD.getAmmount().subtract(valueUAH));

        } else if (CurrencyType.USD.equals(currency)) {
            BigDecimal valueUSD = rate.getSale().multiply(amount);

            if (wUAH.getAmmount().compareTo(valueUSD) < 0) {
                throw new NotEnoughtMoneyException();
            }
            wUAH.setAmmount(wUAH.getAmmount().subtract(valueUSD));
            wUSD.setAmmount(wUSD.getAmmount().add(amount));

        }

        walletRepository.save(wUAH);
        walletRepository.save(wUSD);
    }


}

