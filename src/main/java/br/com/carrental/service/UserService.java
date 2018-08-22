package br.com.carrental.service;

import br.com.carrental.service.dto.UserDTO;
import br.com.carrental.service.exception.DateNotValidException;
import br.com.carrental.service.exception.UserAlreadyExistsException;
import br.com.carrental.service.exception.UserNotFoundException;
import br.com.carrental.model.User;
import br.com.carrental.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public List<UserDTO> getAllUsers() {
        LOGGER.info("m=getAllUsers: GET all users success");

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

        return listDTO;
    }

    public UserDTO getUserById(Long id) throws UserNotFoundException {
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

        return userDTO;
    }

    public void deleteUserById(Long id) throws UserNotFoundException {

        if (!repository.findById(id).isPresent()) {
            LOGGER.warn("m=deleteUserById: DELETE user id = {} not found, ERROR:404", id);
            throw new UserNotFoundException();
        }

        repository.deleteById(id);

        LOGGER.info("m=deleteUserById: DELETE user id = {} success", id);
    }

    public Long saveUser(UserDTO user) throws ParseException, UserAlreadyExistsException, DateNotValidException {
        if (!validDateFormat(user.getBirthDate())) {
            throw new DateNotValidException();
        }

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
            LOGGER.warn("m=saveUser: POST user idDocument = {} has conflict, ERROR:409", user.getIdDocument());
            throw new UserAlreadyExistsException();
        }

        LOGGER.info("m=saveUser: POST user idDocument = {} success", createdUser.getIdDocument());

        return createdUser.getId();
    }

    private boolean validDateFormat(String date) {
        return date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})");
    }

}
