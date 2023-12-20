package com.enigma.tokopakedi.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@Entity
@Table(name = "m_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String address;

    private String phoneNumber;
}
