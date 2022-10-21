package com.vadpol.ex.controller;

import com.vadpol.ex.dto.ExchangeDto;
import com.vadpol.ex.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping("/exchange")
    public void exchange(@RequestBody ExchangeDto exchangeDto) {
        exchangeService.exchange(
                exchangeDto.getPhoneNumber(),
                exchangeDto.getCurrency(),
                exchangeDto.getAmount());
    }



}
