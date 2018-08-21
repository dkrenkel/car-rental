package br.com.carrental.control;

import br.com.carrental.dto.UserDTO;
import br.com.carrental.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl service;

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {

        return service.deleteUserById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO user) throws ParseException {
        return service.addUser(user);
    }

}
