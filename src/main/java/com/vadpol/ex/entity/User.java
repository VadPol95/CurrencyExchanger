package com.vadpol.ex.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @NotEmpty(message = "Name should`t be empty")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @NotEmpty(message = "Name should`t be empty")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    @NotEmpty(message = "Email should`t be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "active")
    private boolean isActive;
}
