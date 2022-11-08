package com.vadpol.ex.controller;

import com.vadpol.ex.dto.TransferDto;
import com.vadpol.ex.dto.WalletDto;
import com.vadpol.ex.entity.TypeEnum;
import com.vadpol.ex.entity.Wallet;
import com.vadpol.ex.service.MoneyOperationService;
import com.vadpol.ex.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class WalletController {

    private final WalletService walletService;
    private final MoneyOperationService operationService;

    @GetMapping("wallet/{phoneNumber}")
    public List<Wallet> getWalletByPhoneNumber(String phoneNumber){
        return walletService.getWalletsByPhoneNumber(phoneNumber);
    }

    @GetMapping("wallet/{id}")
    public WalletDto getWalletById(Long id){
        return walletService.getWalletById(id);
    }

    @PostMapping("/wallet/get")
    public WalletDto getMoney(@RequestBody TransferDto transferDto) {
        return operationService.moneyOperationTransaction(TypeEnum.GET, transferDto);
    }

    @PostMapping("/wallet/put")
    public WalletDto putMoney(@RequestBody TransferDto transferDto) {
        return operationService.moneyOperationTransaction(TypeEnum.PUT, transferDto);
    }

}
