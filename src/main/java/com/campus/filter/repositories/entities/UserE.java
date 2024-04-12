package com.campus.filter.repositories.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserE implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "DNI Can't be Empty")
    @Column(nullable = false, unique = true)
    private String dni;

    @NotEmpty(message = "Name Can't be Empty")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "LastName Can't be Empty")
    @Column(nullable = false)
    private String lastname;

    @Email(message = "Incorrect Email Format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Phone Number can't be Empty")
    @Column(nullable = false)
    private String phone_number;

    @NotEmpty(message = "Can't be Empty")
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private RolType role;

    @NotEmpty(message = "Password can't be Empty")
    @Column(nullable = false)
    private String password;

}