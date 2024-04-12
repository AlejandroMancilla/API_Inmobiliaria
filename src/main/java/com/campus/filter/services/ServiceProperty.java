package com.campus.filter.services;

import java.util.List;

import com.campus.filter.dto.PropertyDTO;
import com.campus.filter.exception.BussinesRuleException;
import com.campus.filter.repositories.entities.PropertyType;
import com.campus.filter.repositories.entities.StateType;

public interface ServiceProperty {

    List<PropertyDTO> findAll();

    PropertyDTO findById(Long id) throws BussinesRuleException;
    
    List<PropertyDTO> findByOfficeId(Long id) throws BussinesRuleException;

    List<PropertyDTO> findByType(PropertyType type) throws BussinesRuleException;

    List<PropertyDTO> findByState(StateType state) throws BussinesRuleException;

    PropertyDTO save(PropertyDTO property);

    PropertyDTO update(Long id, PropertyDTO property);

    void delete(Long id);

}
