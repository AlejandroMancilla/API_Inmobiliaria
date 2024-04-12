package com.campus.filter.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chars")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Name Char Can't be Empty")
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Boolean available;

    @JsonIgnoreProperties(value={"chars", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne()
    @JoinColumn(name = "id_property")
    private Property property;

}
