package com.vadpol.ex.service;

import com.vadpol.ex.dto.TransferDto;
import com.vadpol.ex.dto.WalletDto;
import com.vadpol.ex.entity.TypeEnum;

public interface MoneyOperationService {
    WalletDto moneyOperationTransaction(TypeEnum operationEnum, TransferDto transferInformation);
}
