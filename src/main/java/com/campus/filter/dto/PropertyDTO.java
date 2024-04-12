package com.campus.filter.dto;

import java.util.List;

import com.campus.filter.controllers.OfficeController;
import com.campus.filter.controllers.PropertyController;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class PropertyDTO {
    
    private Long id;

    @JsonView({PropertyController.class, OfficeController.class})
    private String address;

    @JsonView({PropertyController.class, OfficeController.class})
    private Float area;

    @JsonView({PropertyController.class, OfficeController.class})
    private Float price;

    private Long type_id;

    @JsonView({PropertyController.class, OfficeController.class})
    private String type_name;
    
    private Long state_id;

    @JsonView({PropertyController.class, OfficeController.class})
    private String state_name;

    private Long zone_id;

    @JsonView({PropertyController.class, OfficeController.class})
    private String zone_name;
    
    private Long owner_id;

    @JsonView({PropertyController.class, OfficeController.class})
    private String owner_fullname;

    private Long office_id;

    @JsonView({PropertyController.class, OfficeController.class})
    private String office_address;

    @JsonView({PropertyController.class, OfficeController.class})
    private Boolean keys_available;

    @JsonView({PropertyController.class, OfficeController.class})
    private List<CharacterisDTO> chars;

    @JsonView({PropertyController.class, OfficeController.class})
    private List<StayDTO> stays;

    
}
