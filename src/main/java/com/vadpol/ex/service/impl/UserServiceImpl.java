package com.vadpol.ex.service.impl;

import com.vadpol.ex.dto.UserDto;
import com.vadpol.ex.entity.*;
import com.vadpol.ex.exceptions.NonUniqueUser;
import com.vadpol.ex.mapper.MapperConfig;
import com.vadpol.ex.repository.NotificationRepository;
import com.vadpol.ex.repository.UserRepository;
import com.vadpol.ex.repository.WalletRepository;
import com.vadpol.ex.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final NotificationRepository notificationRepository;
    private final MapperConfig mapperConfig;

    @Override
    public Long createUser(UserDto userDto) {
        User user = userRepository.findByPhoneNumber(userDto.getPhoneNumber());

        if(Objects.nonNull(user)){
            throw new NonUniqueUser();
        }

         user = userRepository.save(mapperConfig.map(userDto, User.class));

        Wallet walletUSD = new Wallet()
                .setCurrency(CurrencyEnum.USD)
                .setUser(user)
                .setAmmount(BigDecimal.ZERO)
                .setLastUpdate(new Timestamp(System.currentTimeMillis()));

        Wallet walletUAH = new Wallet()
                .setCurrency(CurrencyEnum.UAH)
                .setUser(user)
                .setAmmount(BigDecimal.ZERO)
                .setLastUpdate(new Timestamp(System.currentTimeMillis()));

        walletRepository.save(walletUAH);
        walletRepository.save(walletUSD);

        Notification not = new Notification()
                .setType(TypeEnum.REGISTRATION)
                .setUserId(user.getId())
                .setContent("Create user : " + user);

        notificationRepository.save(not);

        return user.getId();
    }

    @Override
    public UserDto getUserById(Long id) {
        return mapperConfig.map(userRepository.getOne(id), UserDto.class);
    }

    @Override
    public List<UserDto> getUsers() {
        return mapperConfig.mapAsList(userRepository.findAll(), UserDto.class);
    }
}
