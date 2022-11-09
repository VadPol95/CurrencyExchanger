package com.vadpol.ex.service.impl;

import com.vadpol.ex.config.MapperConfig;
import com.vadpol.ex.dto.TransferDto;
import com.vadpol.ex.dto.WalletDto;
import com.vadpol.ex.entity.Notification;
import com.vadpol.ex.entity.TypeEnum;
import com.vadpol.ex.entity.Wallet;
import com.vadpol.ex.exceptions.WalletNotFoundException;
import com.vadpol.ex.exceptions.NotEnoughtMoneyException;
import com.vadpol.ex.repository.WalletRepository;
import com.vadpol.ex.service.MoneyOperationService;
import com.vadpol.ex.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoneyOperationServiceImpl implements MoneyOperationService {
    private final NotificationService service;
    private final WalletRepository walletRepository;
    private final MapperConfig mapperConfig;

    @Override
    public WalletDto moneyOperationTransaction(TypeEnum operationEnum, TransferDto transferInformation) {
        Optional<Wallet> walletOpt = walletRepository.findById(transferInformation.getId());

        if (walletOpt.isEmpty()) {
            throw new WalletNotFoundException();
        }

        Wallet wallet = walletOpt.get();
        BigDecimal currentAmount = wallet.getAmmount();
        if (TypeEnum.GET.equals(operationEnum)) {
            if (currentAmount.compareTo(transferInformation.getAmount()) < 0) {
                throw new NotEnoughtMoneyException();
            }
            wallet.setAmmount(currentAmount.subtract(transferInformation.getAmount()));
        } else if (TypeEnum.PUT.equals(operationEnum)) {
            wallet.setAmmount(currentAmount.add(transferInformation.getAmount()));
        }
        walletRepository.save(wallet);

        service.createNotification(new Notification()
                .setType(operationEnum)
                .setId(wallet.getUser().getId())
                .setContent(transferInformation.getAmount().toPlainString()));

        return mapperConfig.map(wallet, WalletDto.class);
    }
}
