package com.campus.filter.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.filter.exception.BussinesRuleException;
import com.campus.filter.repositories.RepositoryUser;
import com.campus.filter.repositories.entities.RolType;
import com.campus.filter.repositories.entities.UserE;
import com.campus.filter.services.ServiceUser;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServiceUserImpl implements ServiceUser{
    
    private RepositoryUser repositoryUser;

    @Override
    @Transactional(readOnly = true)
    public List<UserE> findAll() {
        return (List<UserE>) repositoryUser.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserE save(UserE user) {
        return repositoryUser.save(user);
    }

    @Override
    public UserE update(Long id, UserE user) {
        Optional<UserE> userCurrentOptional = repositoryUser.findById(id);

        if(userCurrentOptional.isPresent()){
            UserE userCurrent = userCurrentOptional.get();
            userCurrent.setName(user.getName());
            repositoryUser.save(userCurrent);
            return userCurrent;
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<UserE> userOptional=repositoryUser.findById(id);
        if(userOptional.isPresent()){
            repositoryUser.delete(userOptional.get());
        }   
    }

    @Override
    public UserE findById(Long id) throws BussinesRuleException {
        Optional<UserE> userOptional = repositoryUser.findById(id);
        if(!userOptional.isPresent()){
            BussinesRuleException exception= new BussinesRuleException("1004","Error! User doesn't exist", HttpStatus.PRECONDITION_FAILED);
            throw exception; 
        }
        return userOptional.get();
    }

    public UserE findByEmail(String email) {
        return repositoryUser.findByEmail(email);
    }

    @Override
    public List<UserE> findByRole(RolType role) {
        return repositoryUser.findByRole(role);
    }
}
