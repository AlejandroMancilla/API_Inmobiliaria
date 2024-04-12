package com.campus.filter.config;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.campus.filter.dto.StayDTO;
import com.campus.filter.repositories.entities.Stay;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StayDTOConverter {
    
    private ModelMapper dbm;
    
    public StayDTO convertStayDTO(Stay stay) {
        StayDTO stayDTO = dbm.map(stay, StayDTO.class);
        stayDTO.setName(stay.getName());
        stayDTO.setAmount(stay.getAmount());
        return stayDTO;
    }

    public Stay convertStayAid(StayDTO stayDTO) {
        Stay stayAid = dbm.map(stayDTO, Stay.class);
        return stayAid;
    }

}
