package com.security.practice.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.practice.config.UserRepository;
import com.security.practice.model.User;

@RestController
public class EmployeeRestResource {

	@Autowired
	private UserRepository userRepository;
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllEmps(){
        return  new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/emps/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addEmp(@RequestBody User employee){
       User emp = new User(employee);
       return new ResponseEntity<User>(emp,HttpStatus.CREATED);
    }


    @SuppressWarnings("deprecation")
	@PostMapping("/emps/{id}")
    public ResponseEntity<User> addUser(@PathVariable Long id){
    	User user = userRepository.getById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}