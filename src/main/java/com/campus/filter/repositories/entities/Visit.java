package com.campus.filter.repositories.entities;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "visits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visit implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="visit_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date visitAt;

    @Column(nullable = true)
    private String comment;

    @Schema(description = "Visitor of the Property")
    @ManyToOne()
    @JoinColumn(name = "id_visitor")
    private UserE visitor;

    @Schema(description = "Property to Visit")
    @ManyToOne()
    @JoinColumn(name = "id_property")
    private Property property;

}
