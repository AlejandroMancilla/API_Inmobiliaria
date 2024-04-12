package com.campus.filter.dto;

import java.util.List;

import com.campus.filter.controllers.OfficeController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class OfficeDTO {
    
    private Long id;

    @JsonView(OfficeController.class)
    private String address;

    private Long zone_id;

    @JsonView(OfficeController.class)
    private String zone_name;

    @JsonIgnore
    private List<PropertyDTO> properties;

}
