package com.vadpol.ex.service.impl;

import com.vadpol.ex.entity.User;
import com.vadpol.ex.entity.Wallet;
import com.vadpol.ex.repository.UserRepository;
import com.vadpol.ex.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    private final UserRepository userRepository;
    @Mock
    private final WalletRepository walletRepository;
    @InjectMocks
    WalletServiceImpl walletServiceImpl;
    private static final User IVAN = new User(1L, "Ivan", "Ivanov", "123123", "Ivan@Gmail.com", true);

    WalletServiceImplTest(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    @Test
    void getWalletsByPhoneNumber() {
    }

    @Test
    void getWalletById() {
        Wallet wallet = new Wallet(1L);
        walletRepository.save(wallet);
        Optional<Wallet> maybeWallet = walletRepository.findById(wallet);
        assertTrue(maybeWallet.isPresent());
    }
}