package com.campus.filter.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campus.filter.dto.PropertyDTO;
import com.campus.filter.exception.BussinesRuleException;
import com.campus.filter.repositories.entities.PropertyType;
import com.campus.filter.repositories.entities.StateType;
import com.campus.filter.services.ServiceProperty;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Property Controller", description = "Methods avalaiblaes for Properties")
@RequestMapping("/properties/")
@AllArgsConstructor
public class PropertyController {
    
    private ServiceProperty serviceProperty;

    @Operation(summary = "Get a List with Properties information")
    @GetMapping("/")
    @JsonView(PropertyController.class)
    public ResponseEntity<List<PropertyDTO>> getAllPropertys() {
        List<PropertyDTO> properties = serviceProperty.findAll();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @Operation(summary = "Get a Property by its ID")
    @GetMapping("/{id}")
    @JsonView(PropertyController.class)
    public ResponseEntity<Map<String,Object>> getPropertyById(@PathVariable Long id) throws BussinesRuleException {
        Map<String,Object> response = new HashMap<>();
        PropertyDTO property = serviceProperty.findById(id);
        response.put("property", property);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get a List with Propertys in a given Office")
    @GetMapping("/office/{officeId}")
    @JsonView(PropertyController.class)
    public ResponseEntity<Map<String,Object>> getPropertiesByOfficeId(@PathVariable Long officeId) throws BussinesRuleException {
        Map<String,Object> response = new HashMap<>();
        List<PropertyDTO> propertys = serviceProperty.findByOfficeId(officeId);
        response.put("property", propertys);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get a List with Propertys organised by its Type")
    @GetMapping("/byType/{type}")
    @JsonView(PropertyController.class)
    public ResponseEntity<Map<String,Object>> getPropertiesByTypeId(@PathVariable PropertyType type) throws BussinesRuleException {
        Map<String, Object> response = new HashMap<>();
        List<PropertyDTO> properties = serviceProperty.findByType(type);
        response.put("Property", properties);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get a List with Propertys organised by its State")
    @GetMapping("/byState/{state}")
    @JsonView(PropertyController.class)
    public ResponseEntity<Map<String,Object>> getPropertiesByStateId(@PathVariable StateType state) throws BussinesRuleException {
        Map<String, Object> response = new HashMap<>();
        List<PropertyDTO> properties = serviceProperty.findByState(state);
        response.put("Property", properties);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Create a new Property")
    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody PropertyDTO property, BindingResult result){
        PropertyDTO propertyNew = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Field " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            propertyNew = serviceProperty.save(property);
        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database PropertyDTO");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Property has been successfully created");
        response.put("property", propertyNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update the Property information by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody PropertyDTO property, BindingResult result,
            @PathVariable Long id) {

        PropertyDTO propertyUpdate = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Field " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {

            propertyUpdate = serviceProperty.update(id, property);

        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "Property has been successfully created");
        response.put("property", propertyUpdate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete a Property by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        try {
            serviceProperty.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Property has been successfully deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
