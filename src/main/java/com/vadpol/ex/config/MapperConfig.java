package com.vadpol.ex.config;

import com.vadpol.ex.dto.UserDto;
import com.vadpol.ex.dto.WalletDto;
import com.vadpol.ex.entity.User;
import com.vadpol.ex.entity.Wallet;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MapperConfig extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(User.class, UserDto.class)
                .mapNulls(false)
                .byDefault()
                .register();
        factory.classMap(UserDto.class, User.class)
                .mapNulls(false)
                .byDefault()
                .register();

        factory.classMap(Wallet.class, WalletDto.class)
                .mapNulls(false)
                .byDefault()
                .register();
        factory.classMap(WalletDto.class, Wallet.class)
                .mapNulls(false)
                .byDefault()
                .register();
    }
}
