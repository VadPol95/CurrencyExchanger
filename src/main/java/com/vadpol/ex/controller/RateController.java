package com.vadpol.ex.controller;

import com.vadpol.ex.entity.Rate;
import com.vadpol.ex.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class RateController {

    private final RateService rateService;

    @GetMapping("rate")
    public Rate getCurrentRate(){
        return rateService.getCurrentRate();
    }

}
