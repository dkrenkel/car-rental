package br.com.carrental.control;

import br.com.carrental.service.dto.UserDTO;
import br.com.carrental.service.exception.ConstraintConflictException;
import br.com.carrental.service.exception.UserNotFoundException;
import br.com.carrental.service.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Control class of Users
 *
 * @author Micael
 */

@RestController
public class UserController {
    @Autowired
    private UserService service;

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        LOGGER.info("m=getUsers: Executing");

        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        LOGGER.info("m=getUser: Trying to get user, with id = {}", id);

        try {
            return ResponseEntity.ok(service.getUserById(id));
        } catch (UserNotFoundException e) {
            LOGGER.warn("m=getUser: user with id = {} does not exist, throws {}", id, e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        LOGGER.info("m=deleteUser: Trying to delete user with id = {}", id);

        try {
            service.deleteUserById(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UserNotFoundException e) {
            LOGGER.warn("m=deleteUser: User with id = {} does not exist, throws {}", id, e.toString());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDTO user) {
        LOGGER.info("m=createUser: Trying to POST user with idDocument = {}", user.getIdDocument());

        try {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(service.saveUser(user))
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (ConstraintConflictException e) {
            LOGGER.warn("m=createUser: User with idDocument = {} and email {} has a attribute conflict or already exist, throws ",
                    user.getIdDocument(), user.getEmail(), e);

            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
