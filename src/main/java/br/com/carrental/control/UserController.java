package br.com.carrental.control;

import br.com.carrental.dto.UserDTO;
import br.com.carrental.model.User;
import br.com.carrental.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        List<User> list = repository.findAll();
        List<UserDTO> listDTO = new ArrayList<>();
        for (User user : list) {
            listDTO.add(new UserDTO(user.getIdDocument(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress(),
                    new SimpleDateFormat("dd/MM/yyyy")
                            .format(user.getBirthDate())));
        }
        LOGGER.info("Get all users success");
        return new ResponseEntity(listDTO, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        Optional<User> user = repository.findById(id);

        if (!user.isPresent()) {
            LOGGER.warn("Get user id = {} not found, ERROR: 404", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = new UserDTO(user.get().getIdDocument(),
                user.get().getName(),
                user.get().getEmail(),
                user.get().getAddress(),
                new SimpleDateFormat("dd/MM/yyyy")
                        .format(user.get().getBirthDate()));

        LOGGER.info("Get user id = {} success", id);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {

        if (!repository.findById(id).isPresent()) {
            LOGGER.warn("Delete user id = {} not found, ERROR:404", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        LOGGER.info("Delete user id = {} success", id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO user) throws ParseException {
        User createdUser;
        //verification if the user was already created
        try {
            createdUser = repository
                    .save(new User(user.getIdDocument(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress(),
                    new SimpleDateFormat("dd/MM/yyyy")
                            .parse(user.getBirthDate())));
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Post user idDocument = {} has conflict, ERROR:409", user.getIdDocument());
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        LOGGER.info("Post user idDocument = {} success", createdUser.getIdDocument());
        return ResponseEntity.created(location).build();
    }
}
