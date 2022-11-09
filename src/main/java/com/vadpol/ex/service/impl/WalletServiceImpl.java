package com.vadpol.ex.service.impl;

import com.vadpol.ex.dto.WalletDto;
import com.vadpol.ex.entity.CurrencyType;
import com.vadpol.ex.entity.User;
import com.vadpol.ex.entity.Wallet;
import com.vadpol.ex.exceptions.WalletNotFoundException;
import com.vadpol.ex.exceptions.UserNotFoundException;
import com.vadpol.ex.config.MapperConfig;
import com.vadpol.ex.repository.UserRepository;
import com.vadpol.ex.repository.WalletRepository;
import com.vadpol.ex.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final MapperConfig mapperConfig;


    @Override
    public List<Wallet> getWalletsByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);

        if (Objects.isNull(user)) {
            throw new UserNotFoundException();
        }

        return  walletRepository.findAllByUser(user);
    }

    @Override
    public WalletDto getWalletById(Long id) {
        return walletRepository.findById(id)
                .map(wallet -> mapperConfig.map(wallet, WalletDto.class))
                .orElseThrow(WalletNotFoundException::new);
    }
}

