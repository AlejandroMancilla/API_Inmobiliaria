package com.campus.filter.repositories.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "properties")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Address Can't be Empty")
    @Column(nullable = false)
    private String address;

    @DecimalMin(value="0.0")
    @Column(nullable = false)
    private Float area;

    @DecimalMin(value="0.0")
    @Column(nullable = false)
    private Float price;

    @Column(nullable = true)
    @Enumerated(EnumType.ORDINAL)
    private PropertyType type;

    @Column(nullable = true)
    private Boolean keys_available;

    @Column(nullable = true)
    @Enumerated(EnumType.ORDINAL)
    private StateType state;

    @Schema(description = "Owner of the Property")
    @JsonIgnoreProperties(value={"properties", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @JoinColumn(name = "id_owner", unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private UserE owner;

    @Schema(description = "Characteristics of the Property")
    @JsonIgnore
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Characteristic> chars;

    @Schema(description = "Stancies of the Property")
    @JsonIgnore
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Stay> stays;

    @ManyToOne()
    @JoinColumn(name = "id_office")
    private Office office;

    @JsonIgnoreProperties(value={"zones", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @JoinColumn(name = "id_zone")
    @ManyToOne(fetch = FetchType.LAZY)
    private Zone zone;

    @Schema(description = "Visits of the Property")
    @JsonIgnore
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Visit> visits;
    
}
