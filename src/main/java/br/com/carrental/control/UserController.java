package br.com.carrental.control;

import br.com.carrental.service.UserService;
import br.com.carrental.service.dto.UserDTO;
import br.com.carrental.service.exception.ConstraintConflictException;
import br.com.carrental.service.exception.EntityNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Control class of Users, it provides the HTTP methods of the API.
 *
 * @author Micael
 */

@RestController
@Api
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
    @ApiOperation(value = "Returns all users")
    public ResponseEntity getUsers() {
        LOGGER.info("m=getUsers: Executing");

        return ResponseEntity.ok(service.getAllUsers());
    }

    /**
     * Method that gets user by the database id (to be modified)
     *
     * @param id long - Value indicates the Database id
     * @return ResponseEntity - User with {id} if it is registered. Otherwise it will return Status code 404 (NOT FOUND).
     */
    @GetMapping("/users/{id}")
    @ApiOperation(value = "Returns an user with a given id")
    public ResponseEntity getUser(@PathVariable @ApiParam("An id that will idetify the User to be searched.") final Long id) {
        LOGGER.info("m=getUser: Trying to get user, with id = {}", id);

        try {
            return ResponseEntity.ok(service.getUserById(id));
        } catch (EntityNotFoundException e) {
            LOGGER.warn("m=getUser: user with id = {} does not exist, throws {}", id, e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Method that deletes user by the database id (to be modified)
     *
     * @param id long - Value indicates the Database id
     * @return ResponseEntity - Status code 200 (OK) if user exists and could be deleted. Otherwise it will return Status code 204 (NO CONTENT).
     */
    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "Deletes an user with a given id")
    public ResponseEntity deleteUser(@PathVariable @ApiParam("An id that will idetify the User to be deleted.") final Long id) {
        LOGGER.info("m=deleteUser: Trying to delete user with id = {}", id);

        try {
            service.deleteUserById(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            LOGGER.warn("m=deleteUser: User with id = {} does not exist, throws {}", id, e.toString());

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    /**
     * Method that will post and save a User.
     *
     * @param user UserDTO - Object that has all the information of the User that will be saved.
     * @return ResponseEntity - Status code 201 (CREATED). Otherwise Status code 409 (CONFLICT) if the user already exists.
     */
    @PostMapping("/users")
    @ApiOperation(value = "Creates an user")
    public ResponseEntity<Object> createUser(@RequestBody @Valid @ApiParam("User information for a new User to be created.") final UserDTO user) {
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
