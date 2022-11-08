package com.vadpol.ex.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vadpol.ex.entity.CurrencyType;
import com.vadpol.ex.entity.Notification;
import com.vadpol.ex.entity.Rate;
import com.vadpol.ex.entity.TypeEnum;
import com.vadpol.ex.repository.NotificationRepository;
import com.vadpol.ex.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Component
@Slf4j
@RequiredArgsConstructor
public class CurrentRatesUpdateJob {
    @Value("${privateBank.url}")
    private String fooResourceUrl;
    private final RateRepository rateRepository;
    private final NotificationRepository notificationRepository;

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
    public void getRates() throws JsonProcessingException {
        log.info("start cron job");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl, String.class);
        log.debug(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(response.getBody());

        actualObj.forEach(c -> {
            if (c.get("ccy").asText().equals("USD")) {
                Rate rate = new Rate()
                        .setCurrency(CurrencyType.valueOf(c.get("ccy").asText()))
                        .setBuy(BigDecimal.valueOf(Double.parseDouble(c.get("buy").asText())))
                        .setSale(BigDecimal.valueOf(Double.parseDouble(c.get("sale").asText())))
                        .setReceive(new Timestamp(System.currentTimeMillis()));
                rateRepository.save(rate);
            }
        });

        notificationRepository.save(
                new Notification()
                        .setType(TypeEnum.RATE)
                        .setContent(String.format("Update rate at %s", new Timestamp(System.currentTimeMillis()))));
        log.info("finish cron job");
    }
}
