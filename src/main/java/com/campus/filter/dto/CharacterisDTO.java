package com.campus.filter.dto;

import com.campus.filter.controllers.OfficeController;
import com.campus.filter.controllers.PropertyController;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class CharacterisDTO {
    
    private Long id;

    @JsonView({PropertyController.class, OfficeController.class})
    private String name;

    @JsonView({PropertyController.class, OfficeController.class})
    private Boolean available;

}
