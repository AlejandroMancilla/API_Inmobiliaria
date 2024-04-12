package com.campus.filter.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.campus.filter.config.VisitDTOConverter;
import com.campus.filter.dto.VisitDTO;
import com.campus.filter.exception.BussinesRuleException;
import com.campus.filter.repositories.RepositoryProperty;
import com.campus.filter.repositories.RepositoryUser;
import com.campus.filter.repositories.RepositoryVisit;
import com.campus.filter.repositories.entities.Property;
import com.campus.filter.repositories.entities.UserE;
import com.campus.filter.repositories.entities.Visit;
import com.campus.filter.services.ServiceVisit;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServiceVisitImpl implements ServiceVisit {

    private RepositoryVisit repositoryVisit;
    private RepositoryUser repositoryUser;
    private RepositoryProperty repositoryProperty;

    private VisitDTOConverter convert;

    @Override
    public List<VisitDTO> findAll() {
        List<Visit> visits = (List<Visit>) repositoryVisit.findAll();
        return visits.stream()
                     .map(visit -> convert.convertVisitDTO(visit))
                     .toList();
    }

    @Override
    public VisitDTO findById(Long id) throws BussinesRuleException {
        Optional<Visit> visitOptional = repositoryVisit.findById(id);
        if(!visitOptional.isPresent()){
            BussinesRuleException exception= new BussinesRuleException("1005","Error! Visit doesn't exist", HttpStatus.PRECONDITION_FAILED);
            throw exception; 
        }
        return convert.convertVisitDTO(visitOptional.get());
    }

    @Override
    public List<VisitDTO> findByPropertyId(Long id) throws BussinesRuleException {
        List<Visit> visits = (List<Visit>) repositoryVisit.findByPropertyId(id);
        
        if(visits.isEmpty()) {
            BussinesRuleException exception = new BussinesRuleException("1006","Error! Doesn't exist Visits to the Property: " + id, HttpStatus.PRECONDITION_FAILED);
            throw exception;
        }
        return visits.stream()
                        .map(visit -> convert.convertVisitDTO(visit))
                        .toList();
    }

    @Override
    public List<VisitDTO> findByVisitorId(Long id) throws BussinesRuleException {
        List<Visit> visits = (List<Visit>) repositoryVisit.findByVisitorId(id);
        
        if(visits.isEmpty()) {
            BussinesRuleException exception = new BussinesRuleException("1007","Error! Doesn't exist Visits by the Client: " + id, HttpStatus.PRECONDITION_FAILED);
            throw exception;
        }
        return visits.stream()
                        .map(visit -> convert.convertVisitDTO(visit))
                        .toList();
    }

    @Override
    public VisitDTO save(VisitDTO visit) throws BussinesRuleException {
        Optional<UserE> userOptional = repositoryUser.findById(visit.getVisitor_id());
        Optional<Property> propertyOptional = repositoryProperty.findById(visit.getProperty_id());

        if(userOptional.isPresent() && propertyOptional.isPresent()) {
            Visit visitEntity = convert.convertVisit(visit);
            visitEntity.setVisitor(userOptional.get());
            visitEntity.setProperty(propertyOptional.get());
            visitEntity.setComment(visit.getComment());
            visitEntity.setVisitAt(visit.getDate());;
            repositoryVisit.save(visitEntity);
            return convert.convertVisitDTO(visitEntity);
        }else{
            BussinesRuleException exception = new BussinesRuleException("1007","Error! Visitor or Property don't Exist", HttpStatus.PRECONDITION_FAILED);
            throw exception;
        }
        
               
    }

    @Override
    public VisitDTO update(Long id, VisitDTO visit) {
        Optional<UserE> userOptional = repositoryUser.findById(visit.getVisitor_id());
        Optional<Property> propertyOptional = repositoryProperty.findById(visit.getProperty_id());
        Optional<Visit> visitCurrentOptional = repositoryVisit.findById(id);

        if(userOptional.isPresent() && propertyOptional.isPresent() && visitCurrentOptional.isPresent()) {
            Visit visitCurrent = visitCurrentOptional.get();
            visitCurrent.setVisitor(userOptional.get());
            visitCurrent.setProperty(propertyOptional.get());
            visitCurrent.setVisitAt(visit.getDate());
            visitCurrent.setComment(visit.getComment());
            repositoryVisit.save(visitCurrent);
            return convert.convertVisitDTO(visitCurrent);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Visit> visitOptional = repositoryVisit.findById(id);
        if(visitOptional.isPresent()) {
            repositoryVisit.delete(visitOptional.get());
        }
    }
    
}
