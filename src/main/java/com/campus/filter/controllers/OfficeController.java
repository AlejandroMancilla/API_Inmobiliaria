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

import com.campus.filter.dto.OfficeDTO;
import com.campus.filter.exception.BussinesRuleException;
import com.campus.filter.services.ServiceOffice;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Office Controller", description = "Methods avalaiblaes for Offices")
@RequestMapping("/offices/")
@AllArgsConstructor
public class OfficeController {
    
    private ServiceOffice serviceOffice;
    
    @Operation(summary = "Get a List with Properties information")
    @GetMapping("/")
    @JsonView(OfficeController.class)
    public ResponseEntity<List<OfficeDTO>> getAllOffices() {
        List<OfficeDTO> properties = serviceOffice.findAll();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @Operation(summary = "Get a Office by its ID")
    @GetMapping("/{id}")
    @JsonView(OfficeController.class)
    public ResponseEntity<Map<String,Object>> getOfficeById(@PathVariable Long id) throws BussinesRuleException {
        Map<String,Object> response = new HashMap<>();
        OfficeDTO office = serviceOffice.findById(id);
        response.put("office", office);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get a List with Offices in a specific zone")
    @GetMapping("/byZone/{id}")
    @JsonView(OfficeController.class)
    public ResponseEntity<Map<String,Object>> getPropertiesByZoneId(@PathVariable Long id) throws BussinesRuleException {
        Map<String, Object> response = new HashMap<>();
        List<OfficeDTO> offices = serviceOffice.findByZoneId(id);
        response.put("Office", offices);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Create a new Office")
    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody OfficeDTO office, BindingResult result){
        OfficeDTO officeNew = null;

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
            officeNew = serviceOffice.save(office);
        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database OfficeDTO");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Office has been successfully created");
        response.put("office", officeNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update the Office information by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody OfficeDTO office, BindingResult result,
            @PathVariable Long id) {

        OfficeDTO officeUpdate = null;

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

            officeUpdate = serviceOffice.update(id, office);

        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "Office has been successfully created");
        response.put("office", officeUpdate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete a Office by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        try {
            serviceOffice.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Office has been successfully deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
