package com.campus.filter.dto;

import java.util.Date;

import com.campus.filter.controllers.VisitController;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class VisitDTO {
    
    private Long id;

    private Long property_id;

    @JsonView(VisitController.class)
    private String property_address;

    private Long visitor_id;

    @JsonView(VisitController.class)
    private String visitor_fullname;

    @JsonView(VisitController.class)
    private Date date;

    @JsonView(VisitController.class)
    private String Comment;

}
