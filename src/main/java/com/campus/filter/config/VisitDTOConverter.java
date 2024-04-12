package com.campus.filter.config;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.campus.filter.dto.VisitDTO;
import com.campus.filter.repositories.entities.Visit;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VisitDTOConverter {
    
    private ModelMapper dbm;

    public VisitDTO convertVisitDTO(Visit visit){

        VisitDTO visitDTO = dbm.map(visit, VisitDTO.class);
        visitDTO.setProperty_address(visit.getProperty().getAddress());
        visitDTO.setVisitor_fullname(visit.getVisitor().getName() + " " + visit.getVisitor().getLastname());
        visitDTO.setDate(visit.getVisitAt());
        visitDTO.setComment(visit.getComment());
        return visitDTO;
    }

    public Visit convertVisit(VisitDTO visitDTO) {
        Visit visit = dbm.map(visitDTO, Visit.class);
        return visit;
    }
}
