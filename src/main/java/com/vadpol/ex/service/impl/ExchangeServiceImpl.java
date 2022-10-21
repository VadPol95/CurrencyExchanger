package com.vadpol.ex.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadpol.ex.entity.*;
import com.vadpol.ex.exceptions.NotEnoughtMoneyException;
import com.vadpol.ex.repository.NotificationRepository;
import com.vadpol.ex.repository.RateRepository;
import com.vadpol.ex.repository.WalletRepository;
import com.vadpol.ex.service.ExchangeService;
import com.vadpol.ex.service.RateService;
import com.vadpol.ex.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final RateRepository rateRepository;
    private final NotificationRepository notificationRepository;
    private final WalletService walletService;
    private final RateService rateService;
    private final WalletRepository walletRepository;

    @Scheduled(cron = "0 * * * * MON-FRI") // means once per minute on weekdays
    /*
    +-------------------- second (0 - 59)
    |  +----------------- minute (0 - 59)
    |  |  +-------------- hour (0 - 23)
    |  |  |  +----------- day of month (1 - 31)
    |  |  |  |  +-------- month (1 - 12)
    |  |  |  |  |  +----- day of week (0 - 6) (Sunday=0 or 7)
    |  |  |  |  |  |  +-- year [optional]
    |  |  |  |  |  |  |
    *  *  *  *  *  *  * command to be executed
     */
    @Override
    public void getRates() throws JsonProcessingException {
        log.info("start cron job");
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl, String.class);
        log.error(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(response.getBody());

        actualObj.forEach(c -> {
            if (!c.get("ccy").asText().equals("BTC")
                    && !c.get("ccy").asText().equals("RUR")
                    && !c.get("ccy").asText().equals("EUR")) {
                Rate rate = new Rate()
                        .setBuy(BigDecimal.valueOf(Double.parseDouble(c.get("buy").asText())))
                        .setSale(BigDecimal.valueOf(Double.parseDouble(c.get("sale").asText())))
                        .setReceive(new Timestamp(System.currentTimeMillis()))
                        .setCurrency(CurrencyEnum.valueOf(c.get("ccy").asText()));
                rateRepository.save(rate);
            }
        });

        notificationRepository.save(
                new Notification()
                        .setType(TypeEnum.RATE)
                        .setContent(String.format("Update rate at %s", new Timestamp(System.currentTimeMillis()))));
        log.info("finish cron job");
    }

    @Override
    public void exchange(String phoneNumber, CurrencyEnum currency, BigDecimal amount) {
        List<Wallet> walletList = walletService.getWalletsByPhoneNumber(phoneNumber);

        Wallet wUAH = walletList.stream().filter(w -> w.getCurrency().equals(CurrencyEnum.UAH)).findFirst().get();
        Wallet wUSD = walletList.stream().filter(w -> w.getCurrency().equals(CurrencyEnum.USD)).findFirst().get();

        Rate rate = rateService.getCurrentRate();

        BigDecimal value;

        if (CurrencyEnum.UAH.equals(currency)) {
            value = amount.divide(rate.getBuy(), RoundingMode.HALF_UP);
            if (wUSD.getAmmount().compareTo(value) < 0) {
                throw new NotEnoughtMoneyException();
            }
            wUAH.setAmmount(wUAH.getAmmount().add(amount));
            wUSD.setAmmount(wUSD.getAmmount().subtract(value));

        } else if (CurrencyEnum.USD.equals(currency)) {
            value = rate.getSale().multiply(amount);

            if (wUAH.getAmmount().compareTo(value) < 0) {
                throw new NotEnoughtMoneyException();
            }
            wUAH.setAmmount(wUAH.getAmmount().subtract(value));
            wUSD.setAmmount(wUSD.getAmmount().add(amount));

        }

        walletRepository.save(wUAH);
        walletRepository.save(wUSD);
    }


}

