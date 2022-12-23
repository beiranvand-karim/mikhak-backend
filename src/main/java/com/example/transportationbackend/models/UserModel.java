package com.example.transportationbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "user_tb")
public class UserModel {
    @Id
    @Column(name = "id",
            nullable = false,
            unique = true)
    private String id;

    @Column(unique = true)
    private String password;

    @Column(unique = true,name = "email_address")
    private String emailAddress;

    @Column(unique = true,name = "phone")
    private String phone;

    private String role;
}
