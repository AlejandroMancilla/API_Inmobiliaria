package com.campus.filter.config;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.campus.filter.dto.OfficeDTO;
import com.campus.filter.repositories.entities.Office;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OfficeDTOConverter {
    
    private ModelMapper dbm;

    public OfficeDTO convertOfficeDTO(Office office){

        OfficeDTO officeDTO = dbm.map(office, OfficeDTO.class);
        officeDTO.setAddress(office.getAddress());
        if(office.getZone().getName() == null){
            officeDTO.setZone_name("Zone");
        }else{
            officeDTO.setZone_name(office.getZone().getName());
        }
        
        return officeDTO;
    }

    public Office convertOffice(OfficeDTO officeDTO) {
        Office office = dbm.map(officeDTO, Office.class);
        return office;
    }
}
