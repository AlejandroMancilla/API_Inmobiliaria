package com.campus.filter.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.campus.filter.repositories.entities.Property;
import com.campus.filter.repositories.entities.PropertyType;
import com.campus.filter.repositories.entities.StateType;

public interface RepositoryProperty extends CrudRepository<Property, Long> {
    
    List<Property> findByOfficeId(Long id);

    List<Property> findByType(PropertyType type);

    List<Property> findByState(StateType state);
    
}
