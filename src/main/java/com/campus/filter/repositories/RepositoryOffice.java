package com.campus.filter.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.campus.filter.repositories.entities.Office;

public interface RepositoryOffice extends CrudRepository<Office, Long> {
    
    List<Office> findByZoneId(Long id);

}
