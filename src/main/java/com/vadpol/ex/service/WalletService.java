package com.vadpol.ex.service;

import com.vadpol.ex.dto.TransferDto;
import com.vadpol.ex.dto.WalletDto;
import com.vadpol.ex.entity.TypeEnum;
import com.vadpol.ex.entity.Wallet;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    List<Wallet> getWalletsByPhoneNumber(String phoneNumber);

    WalletDto getWalletById(Long id);

    void moneyOperation(String phoneNumber, TypeEnum operationEnum, BigDecimal amount);

    WalletDto moneyOperationTransaction(TypeEnum operationEnum, TransferDto transferInformation);
}
