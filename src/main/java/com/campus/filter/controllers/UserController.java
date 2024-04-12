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

import com.campus.filter.exception.BussinesRuleException;
import com.campus.filter.repositories.entities.RolType;
import com.campus.filter.repositories.entities.UserE;
import com.campus.filter.services.ServiceUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "User_Controller", description = "Methods availables for Users")
@RequestMapping("/users/")
@AllArgsConstructor
public class UserController {
    
    private ServiceUser serviceUser;
    
    @Operation(summary = "Get a List with Users information")
    @GetMapping("/")
    public ResponseEntity<List<UserE>> findAll() {
        List<UserE> findAll = serviceUser.findAll();
        if(findAll == null || findAll.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(findAll);
        }
    }

    @Operation(summary = "Get a User by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> findAllById(@PathVariable Long id)throws BussinesRuleException{
         Map<String,Object> response = new HashMap<>();
         UserE user = serviceUser.findById(id);
         response.put("user",  user);
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get a User by its Email")
    @GetMapping("/byEmail/{email}")
    public ResponseEntity<Map<String,Object>> findByEmail(@PathVariable String email) {

         Map<String,Object> response=new HashMap<>();

         UserE user=serviceUser.findByEmail(email);

         if(user!=null){
            response.put("user",user);
            return new ResponseEntity<>(response,HttpStatus.OK);
         }else{
            response.put("mensaje",new String("No existe ningún user con ese id:"));
            return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
         }
    }

    @Operation(summary = "Get a list of users with a given role")
    @GetMapping("/byRole/{role}")
    public ResponseEntity<List<UserE>> findByRole(@PathVariable RolType role) {
        List<UserE> users = serviceUser.findByRole(role);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Create a new User")
    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody UserE user, BindingResult result) {

        UserE userNew = null;

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
            userNew = serviceUser.save(user);
        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "User has been successfully created");
        response.put("user", userNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update the User information by its ID")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody UserE user, BindingResult result,
            @PathVariable Long id) {

        UserE userUpdate = null;

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

            userUpdate = serviceUser.update(id, user);

        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "User has been successfully Updated");
        response.put("user", userUpdate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete a User by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        try {
            serviceUser.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error when inserting in the database");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "User has been successfully deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }  

}
