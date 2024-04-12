package com.campus.filter.config;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.campus.filter.dto.CharacterisDTO;
import com.campus.filter.repositories.entities.Characteristic;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CharacterisDTOConverter {

    private ModelMapper dbm;
    
    public CharacterisDTO convertCharacterisDTO(Characteristic charac) {
        
        CharacterisDTO characterisDTO = dbm.map(charac, CharacterisDTO.class);
        characterisDTO.setName(charac.getName());
        characterisDTO.setAvailable(charac.getAvailable());
        return characterisDTO;

    }

    public Characteristic convertCharacterisAid(CharacterisDTO characterisDTO) {
        
        Characteristic characterisAid = dbm.map(characterisDTO, Characteristic.class);
        return characterisAid;

    }

}
