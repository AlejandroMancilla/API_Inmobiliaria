package com.campus.filter.services;

import java.util.List;

import com.campus.filter.dto.OfficeDTO;
import com.campus.filter.exception.BussinesRuleException;

public interface ServiceOffice {
    
    List<OfficeDTO> findAll();

    OfficeDTO findById(Long id) throws BussinesRuleException;
    
    List<OfficeDTO> findByZoneId(Long id) throws BussinesRuleException;

    OfficeDTO save(OfficeDTO office);

    OfficeDTO update(Long id, OfficeDTO office);

    void delete(Long id);
    
}
