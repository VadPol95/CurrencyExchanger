package com.vadpol.ex.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "notification")
@Accessors(chain = true)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    TypeEnum type;

    String content;

    @Column(name = "user_id")
    long userId;

}
