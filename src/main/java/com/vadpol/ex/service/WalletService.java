package com.vadpol.ex.service;

import com.vadpol.ex.dto.WalletDto;
import com.vadpol.ex.entity.CurrencyType;
import com.vadpol.ex.entity.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletService {
    List<Wallet> getWalletsByPhoneNumber(String phoneNumber);

    WalletDto getWalletById(Long id);
}
