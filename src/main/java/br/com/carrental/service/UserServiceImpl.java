package br.com.carrental.service;

import br.com.carrental.service.dto.UserDTO;
import br.com.carrental.service.exception.UserAlreadyExistsException;
import br.com.carrental.service.exception.UserNotFoundException;
import br.com.carrental.model.User;
import br.com.carrental.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository repository;

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public ResponseEntity getAllUsers() {
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
        LOGGER.info("m=getAllUsers: GET all users success");
        return new ResponseEntity(listDTO, HttpStatus.OK);
    }

    public ResponseEntity getUserById(@PathVariable Long id) throws UserNotFoundException {
        Optional<User> user = repository.findById(id);

        if (!user.isPresent()) {
            LOGGER.warn("m=getUserById: GET user id = {} not found, ERROR: 404", id);
            throw new UserNotFoundException();
        }

        UserDTO userDTO = new UserDTO(user.get().getIdDocument(),
                user.get().getName(),
                user.get().getEmail(),
                user.get().getAddress(),
                new SimpleDateFormat("dd/MM/yyyy")
                        .format(user.get().getBirthDate()));

        LOGGER.info("m=getUserById: GET user id = {} success", id);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    public ResponseEntity deleteUserById(@PathVariable Long id) throws UserNotFoundException {
        if (!repository.findById(id).isPresent()) {
            LOGGER.warn("m=deleteUserById: DELETE user id = {} not found, ERROR:404", id);
            throw new UserNotFoundException();
            //return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        LOGGER.info("m=deleteUserById: DELETE user id = {} success", id);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<Object> addUser(@RequestBody UserDTO user) throws ParseException, UserAlreadyExistsException {
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
            LOGGER.warn("m=addUser: POST user idDocument = {} has conflict, ERROR:409", user.getIdDocument());
            throw new UserAlreadyExistsException();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        LOGGER.info("m=addUser: POST user idDocument = {} success", createdUser.getIdDocument());
        return ResponseEntity.created(location).build();
    }

}
