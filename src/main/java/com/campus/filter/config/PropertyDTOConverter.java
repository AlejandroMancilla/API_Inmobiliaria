package com.campus.filter.config;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.campus.filter.dto.PropertyDTO;
import com.campus.filter.repositories.entities.Property;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PropertyDTOConverter {
    
    private ModelMapper dbm;

    private CharacterisDTOConverter characterisDTOConverter;
    private StayDTOConverter stayDTOConverter;

    public PropertyDTO convertPropertyDTO(Property property){

        PropertyDTO propertyDTO = dbm.map(property, PropertyDTO.class);
        propertyDTO.setAddress(property.getAddress());
        propertyDTO.setArea(property.getArea());
        propertyDTO.setPrice(property.getPrice());
        propertyDTO.setType_name(property.getType().name());
        propertyDTO.setState_name(property.getState().name());
        propertyDTO.setZone_name(property.getZone().getName());
        propertyDTO.setOwner_fullname(property.getOwner().getName() + " " + property.getOwner().getLastname());
        propertyDTO.setOffice_address(property.getOffice().getAddress());
        propertyDTO.setKeys_available(property.getKeys_available());
        propertyDTO.setChars(property.getChars()
                        .stream()
                        .map(charac -> characterisDTOConverter.convertCharacterisDTO(charac))
                        .toList());
        propertyDTO.setStays(property.getStays()
                        .stream()
                        .map(stay -> stayDTOConverter.convertStayDTO(stay))
                        .toList());
        return propertyDTO;
    }

    public Property convertProperty(PropertyDTO propertyDTO) {
        Property property = dbm.map(propertyDTO, Property.class);
        return property;
    }
}
