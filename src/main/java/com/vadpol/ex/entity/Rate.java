package com.vadpol.ex.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "rate")
@Accessors(chain = true)
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "currency")
    CurrencyType currency;
    @Column(name = "sale")
    BigDecimal sale;
    @Column(name = "buy")
    BigDecimal buy;
    @Column(name = "receive")
    Timestamp receive;
}
