package com.campus.filter.repositories;

import org.springframework.data.repository.CrudRepository;

import com.campus.filter.repositories.entities.Stay;

public interface RepositoryStay extends CrudRepository<Stay, Long> {
    
}
