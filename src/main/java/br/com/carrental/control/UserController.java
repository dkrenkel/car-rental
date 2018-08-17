package br.com.carrental.control;

import br.com.carrental.dto.UserDTO;
import br.com.carrental.model.User;
import br.com.carrental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

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
        return new ResponseEntity(listDTO, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        Optional<User> user = repository.findById(id);

        if (!user.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = new UserDTO(user.get().getIdDocument(),
                user.get().getName(),
                user.get().getEmail(),
                user.get().getAddress(),
                new SimpleDateFormat("dd/MM/yyyy")
                        .format(user.get().getBirthDate()));


        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {

        if (!repository.findById(id).isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User createdUser;
        //verification if the user was already created
        try {
            createdUser = repository.save(user);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /*
     *@GetMapping("/users")
     *public List<User> getUsers(){
     *    return repository.findAll();
     *}
     */
}
