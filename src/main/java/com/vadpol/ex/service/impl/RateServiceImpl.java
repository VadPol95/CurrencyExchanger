package com.vadpol.ex.service.impl;

import com.vadpol.ex.entity.Rate;
import com.vadpol.ex.repository.RateRepository;
import com.vadpol.ex.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    public final RateRepository rateRepository;

    public Rate getCurrentRate() {
        LinkedList<Rate> rates = new LinkedList<>(rateRepository.findAll());
        return rates.getLast();
    }
}
