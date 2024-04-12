package com.campus.filter.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.campus.filter.config.OfficeDTOConverter;
import com.campus.filter.dto.OfficeDTO;
import com.campus.filter.exception.BussinesRuleException;
import com.campus.filter.repositories.RepositoryOffice;
import com.campus.filter.repositories.RepositoryZone;
import com.campus.filter.repositories.entities.Office;
import com.campus.filter.repositories.entities.Zone;
import com.campus.filter.services.ServiceOffice;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServiceOfficeImpl implements ServiceOffice{

    private RepositoryOffice repositoryOffice;
    private RepositoryZone repositoryZone;

    private OfficeDTOConverter convert;

    @Override
    public List<OfficeDTO> findAll() {
       List<Office> offices = (List<Office>) repositoryOffice.findAll();
        return offices.stream()
                     .map(office -> convert.convertOfficeDTO(office))
                     .toList();
    }

    @Override
    public OfficeDTO findById(Long id) throws BussinesRuleException {
        Optional<Office> propertOptional = repositoryOffice.findById(id);
        if(!propertOptional.isPresent()){
            BussinesRuleException exception= new BussinesRuleException("1008","Error! Office doesn't exist", HttpStatus.PRECONDITION_FAILED);
            throw exception; 
        }
        return convert.convertOfficeDTO(propertOptional.get());
    }

    @Override
    public List<OfficeDTO> findByZoneId(Long id) throws BussinesRuleException {
        List<Office> offices = (List<Office>) repositoryOffice.findByZoneId(id);
        if(offices.isEmpty()) {
            BussinesRuleException exception = new BussinesRuleException("1004","Error! Doesn't exist Offices in the zone: " + id, HttpStatus.PRECONDITION_FAILED);
            throw exception;
        }
        return offices.stream()
                        .map(office -> convert.convertOfficeDTO(office))
                        .toList();
    }

    @Override
    public OfficeDTO save(OfficeDTO office) {
        Optional<Zone> zoneOptional = repositoryZone.findById(office.getZone_id());
                
        if(zoneOptional.isPresent()) {
            Office officeEntity = convert.convertOffice(office);
            officeEntity.setZone(zoneOptional.get());
            return convert.convertOfficeDTO(officeEntity);
        }
        return null;
    }

    @Override
    public OfficeDTO update(Long id, OfficeDTO office) {
        Optional<Zone> zoneOptional = repositoryZone.findById(office.getZone_id());
        Optional<Office> officeCurrentOptional = repositoryOffice.findById(id);        
        
        if(zoneOptional.isPresent() && officeCurrentOptional.isPresent()) {
            Office officeEntity = convert.convertOffice(office);
            officeEntity.setZone(zoneOptional.get());
            return convert.convertOfficeDTO(officeEntity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Office> officeOptional=repositoryOffice.findById(id);
        if(officeOptional.isPresent()){
            repositoryOffice.delete(officeOptional.get());
        }   
    }
    
}
