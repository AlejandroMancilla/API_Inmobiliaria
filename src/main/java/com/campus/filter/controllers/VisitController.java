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

import com.campus.filter.dto.VisitDTO;
import com.campus.filter.exception.BussinesRuleException;
import com.campus.filter.services.ServiceVisit;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Visit Controller", description = "Methods avalaiblaes for Visits")
@RequestMapping("/visits/")
@AllArgsConstructor
public class VisitController {
    
    private ServiceVisit serviceVisit;
    
    @Operation(summary = "Get a List with Visits information")
    @GetMapping("/")
    @JsonView(VisitController.class)
    public ResponseEntity<List<VisitDTO>> findAll() {
        List<VisitDTO> visits= serviceVisit.findAll();
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @Operation(summary = "Get a Visit by its ID")
    @GetMapping("/{id}")
    @JsonView(VisitController.class)
    public ResponseEntity<Map<String,Object>> getVisitById(@PathVariable Long id) throws BussinesRuleException {
        Map<String,Object> response = new HashMap<>();
        VisitDTO visit = serviceVisit.findById(id);
        response.put("visit", visit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get a List with Visits to a given Property")
    @GetMapping("/property/{propertyId}")
    @JsonView(VisitController.class)
    public ResponseEntity<Map<String,Object>> getVisitByPropertyId(@PathVariable Long propertyId) throws BussinesRuleException {
        Map<String,Object> response = new HashMap<>();
        List<VisitDTO> visits = serviceVisit.findByPropertyId(propertyId);
        response.put("visit", visits);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get a List with Visits made by a given Client")
    @GetMapping("/visitor/{visitorId}")
    @JsonView(VisitController.class)
    public ResponseEntity<Map<String,Object>> getVisitByVisitorId(@PathVariable Long visitorId) throws BussinesRuleException {
        Map<String,Object> response = new HashMap<>();
        List<VisitDTO> visits = serviceVisit.findByVisitorId(visitorId);
        response.put("visit", visits);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Create a new Visit")
    @PostMapping("/")
    @JsonView(VisitController.class)
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody VisitDTO visit, BindingResult result) throws BussinesRuleException{
        VisitDTO visitNew = null;

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
            visitNew = serviceVisit.save(visit);
        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database VisitDTO");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Visit has been successfully created");
        response.put("visit", visitNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update the Visit information by its ID")
    @PutMapping("/{id}")
    @JsonView(VisitController.class)
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody VisitDTO visit, BindingResult result,
            @PathVariable Long id) {

        VisitDTO visitUpdate = null;

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

            visitUpdate = serviceVisit.update(id, visit);

        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "Visit has been successfully created");
        response.put("visit", visitUpdate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete a Visit by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        try {
            serviceVisit.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Visit has been successfully deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
