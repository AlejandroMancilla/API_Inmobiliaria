package com.campus.filter.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.campus.filter.repositories.entities.RolType;
import com.campus.filter.repositories.entities.UserE;

public interface RepositoryUser extends CrudRepository<UserE, Long> {
    
    UserE findByEmail(String email);

    List<UserE> findByRole(RolType role);

}
