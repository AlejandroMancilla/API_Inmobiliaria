package com.campus.filter.repositories;

import org.springframework.data.repository.CrudRepository;

import com.campus.filter.repositories.entities.Characteristic;

public interface RepositoryCharacteristic extends CrudRepository<Characteristic, Long> {
    
}
