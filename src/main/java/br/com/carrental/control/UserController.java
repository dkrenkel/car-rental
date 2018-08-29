package br.com.carrental.control;

import br.com.carrental.service.UserService;
import br.com.carrental.service.dto.UserDTO;
import br.com.carrental.service.exception.ConstraintConflictException;
import br.com.carrental.service.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Control class of Users, it provides the HTTP methods of the API.
 *
 * @author Micael
 */

@RestController
public class UserController {
    @Autowired
    private UserService service;

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * Method that gets all users registered on system.
     *
     * @return ResponseEntity - List of all users
     */
    @GetMapping("/users")
    public ResponseEntity getUsers() {
        LOGGER.info("m=getUsers: Executing");

        return ResponseEntity.ok(service.getAllUsers());
    }

    /**
     * Method that gets user by the database id (to be modified)
     *
     * @param id long - Value indicates the Database id
     *
     * @return ResponseEntity - User with {id} if it is registered. Otherwise it will return Status code 404 (NOT FOUND).
     */
    @GetMapping("/users/{id}")
    public ResponseEntity getUser(@PathVariable final Long id) {
        LOGGER.info("m=getUser: Trying to get user, with id = {}", id);

        try {
            return ResponseEntity.ok(service.getUserById(id));
        } catch (UserNotFoundException e) {
            LOGGER.warn("m=getUser: user with id = {} does not exist, throws {}", id, e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Method that deletes user by the database id (to be modified)
     *
     * @param id long - Value indicates the Database id
     *
     * @return ResponseEntity - Status code 200 (OK) if user exists and could be deleted. Otherwise it will return Status code 404 (NOT FOUND).
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable final Long id) {
        LOGGER.info("m=deleteUser: Trying to delete user with id = {}", id);

        try {
            service.deleteUserById(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UserNotFoundException e) {
            LOGGER.warn("m=deleteUser: User with id = {} does not exist, throws {}", id, e.toString());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Method that will post and save a User.
     *
     * @param user UserDTO - Object that has all the information of the User that will be saved.
     *
     * @return ResponseEntity - Status code 201 (CREATED). Otherwise Status code 409 (CONFLICT) if the user already exists.
     */
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody @Valid final UserDTO user) {
        LOGGER.info("m=createUser: Trying to POST user with idDocument = {}", user.getIdDocument());

        try {
            final URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(service.saveUser(user))
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (ConstraintConflictException e) {
            LOGGER.warn("m=createUser: User with idDocument = {} and email {} has an attribute conflict or already exist, throws ",
                    user.getIdDocument(), user.getEmail(), e);

            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
