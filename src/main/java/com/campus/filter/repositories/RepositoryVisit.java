package com.campus.filter.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.campus.filter.repositories.entities.Visit;

public interface RepositoryVisit extends CrudRepository<Visit, Long> {
    
    List<Visit> findByPropertyId(Long id);

    List<Visit> findByVisitorId(Long id);
}
