package br.com.carrental.control;

import br.com.carrental.service.dto.UserDTO;
import br.com.carrental.service.exception.UserAlreadyExistsException;
import br.com.carrental.service.exception.UserNotFoundException;
import br.com.carrental.service.UserServiceImpl;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl service;

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        LOGGER.info("m=getUsers: Success");
        return service.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        try {
            LOGGER.info("m=getUser: Trying to get user, with id = {}", id);
            return service.getUserById(id);
        } catch (UserNotFoundException e) {
            LOGGER.warn("m=getUser: id = {} throws {}", id, e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            LOGGER.info("m=deleteUser: Trying to delete user with id = {}", id);
            return service.deleteUserById(id);
        } catch (UserNotFoundException e) {
            LOGGER.warn("m=deleteUser: id = {} throws {}", id, e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO user) throws ParseException {
        try {
            LOGGER.info("m=createUser: Trying to POST user with idDocument = {}", user.getIdDocument());
            return service.addUser(user);
        } catch (UserAlreadyExistsException e) {
            LOGGER.warn("m=createUser: idDocument = {} throws {}", user.getIdDocument(), e.toString());
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

}
