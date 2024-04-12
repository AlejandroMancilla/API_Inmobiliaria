package com.campus.filter.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campus.filter.config.CharacterisDTOConverter;
import com.campus.filter.config.PropertyDTOConverter;
import com.campus.filter.config.StayDTOConverter;
import com.campus.filter.dto.CharacterisDTO;
import com.campus.filter.dto.PropertyDTO;
import com.campus.filter.dto.StayDTO;
import com.campus.filter.exception.BussinesRuleException;
import com.campus.filter.repositories.RepositoryCharacteristic;
import com.campus.filter.repositories.RepositoryOffice;
import com.campus.filter.repositories.RepositoryProperty;
import com.campus.filter.repositories.RepositoryStay;
import com.campus.filter.repositories.RepositoryUser;
import com.campus.filter.repositories.RepositoryZone;
import com.campus.filter.repositories.entities.Characteristic;
import com.campus.filter.repositories.entities.Office;
import com.campus.filter.repositories.entities.Property;
import com.campus.filter.repositories.entities.PropertyType;
import com.campus.filter.repositories.entities.StateType;
import com.campus.filter.repositories.entities.Stay;
import com.campus.filter.repositories.entities.UserE;
import com.campus.filter.repositories.entities.Zone;
import com.campus.filter.services.ServiceProperty;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServicePropertyImpl implements ServiceProperty{
    
    private RepositoryProperty repositoryProperty;
    private RepositoryUser repositoryUser;
    private RepositoryOffice repositoryOffice;
    private RepositoryCharacteristic repositoryCharacteristic;
    private RepositoryStay repositoryStay;
    private RepositoryZone repositoryZone;

    private PropertyDTOConverter convert;
    private CharacterisDTOConverter convertChar;
    private StayDTOConverter convertStay;

    @Override
    @Transactional(readOnly = true)
    public List<PropertyDTO> findAll() {
        List<Property> propertys = (List<Property>) repositoryProperty.findAll();
        return propertys.stream()
                     .map(property -> convert.convertPropertyDTO(property))
                     .toList();
    }

    @Override
    public PropertyDTO findById(Long id) throws BussinesRuleException {
       Optional<Property> propertOptional = repositoryProperty.findById(id);
        if(!propertOptional.isPresent()){
            BussinesRuleException exception= new BussinesRuleException("1001","Error! Property doesn't exist", HttpStatus.PRECONDITION_FAILED);
            throw exception; 
        }
        return convert.convertPropertyDTO(propertOptional.get());
    }

    @Override
    public List<PropertyDTO> findByOfficeId(Long id) throws BussinesRuleException {
        List<Property> properties = (List<Property>) repositoryProperty.findByOfficeId(id);
        
        if(properties.isEmpty()) {
            BussinesRuleException exception = new BussinesRuleException("1002","Error! Doesn't exist Propertis in the Office " + id, HttpStatus.PRECONDITION_FAILED);
            throw exception;
        }
        return properties.stream()
                        .map(property -> convert.convertPropertyDTO(property))
                        .toList();
    }

    @Override
    public List<PropertyDTO> findByType(PropertyType type) throws BussinesRuleException {
        List<Property> properties = (List<Property>) repositoryProperty.findByType(type);
        
        if(properties.isEmpty()) {
            BussinesRuleException exception = new BussinesRuleException("1003","Error! Doesn't exist Propertis with Type: " + type, HttpStatus.PRECONDITION_FAILED);
            throw exception;
        }
        return properties.stream()
                        .map(property -> convert.convertPropertyDTO(property))
                        .toList();
    }

    @Override
    public List<PropertyDTO> findByState(StateType state) throws BussinesRuleException {
        List<Property> properties = (List<Property>) repositoryProperty.findByState(state);
        
        if(properties.isEmpty()) {
            BussinesRuleException exception = new BussinesRuleException("1004","Error! Doesn't exist Propertis with Status: " + state, HttpStatus.PRECONDITION_FAILED);
            throw exception;
        }
        return properties.stream()
                        .map(property -> convert.convertPropertyDTO(property))
                        .toList();
    }

    @Override
    public PropertyDTO save(PropertyDTO property) {
        Optional<UserE> userOptional = repositoryUser.findById(property.getOwner_id());
        Optional<Office> officeOptional = repositoryOffice.findById(property.getOffice_id());
        Optional<Zone> zoneOptional = repositoryZone.findById(property.getZone_id());
        
        if(userOptional.isPresent() && officeOptional.isPresent()) {
            Property propertyEntity = convert.convertProperty(property);
            propertyEntity.setOwner(userOptional.get());
            propertyEntity.setOffice(officeOptional.get());
            propertyEntity.setAddress(property.getAddress());
            propertyEntity.setType(PropertyType.fromValue(property.getType_id()));
            propertyEntity.setState(StateType.fromValue(property.getState_id()));
            propertyEntity.setArea(property.getArea());
            propertyEntity.setKeys_available(property.getKeys_available());
            propertyEntity.setPrice(property.getPrice());
            propertyEntity.setZone(zoneOptional.get());
            repositoryProperty.save(propertyEntity);
            for (CharacterisDTO charas : property.getChars()) {
                Characteristic characEntity = convertChar.convertCharacteris(charas);
                characEntity.setProperty(propertyEntity);
                repositoryCharacteristic.save(characEntity);
            }
            for (StayDTO stay : property.getStays()) {
                Stay stayEntity = convertStay.convertStay(stay);
                stayEntity.setProperty(propertyEntity);
                repositoryStay.save(stayEntity);
            }
            return convert.convertPropertyDTO(propertyEntity);
        }
        return null;
    }

    @Override
    public PropertyDTO update(Long id, PropertyDTO property) {
        Optional<UserE> userOptional = repositoryUser.findById(property.getOwner_id());
        Optional<Office> officeOptional = repositoryOffice.findById(property.getOffice_id());
        Optional<Property> properCurrentOptional = repositoryProperty.findById(id);

        if(userOptional.isPresent() && officeOptional.isPresent() && properCurrentOptional.isPresent()) {
            Property propertyCurrent = properCurrentOptional.get();
            propertyCurrent.setOwner(userOptional.get());
            propertyCurrent.setOffice(officeOptional.get());
            repositoryProperty.save(propertyCurrent);
            return convert.convertPropertyDTO(propertyCurrent);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Property> properOptional = repositoryProperty.findById(id);
        if(properOptional.isPresent()) {
            repositoryProperty.delete(properOptional.get());       
        }
    }




}
