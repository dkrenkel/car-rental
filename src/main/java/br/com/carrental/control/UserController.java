package br.com.carrental.control;

import br.com.carrental.model.User;
import br.com.carrental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    public List<User> getUsers(){
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserWithThisID(@PathVariable String id){
        Optional<User> user = repository.findById(id);

        if(!user.isPresent()) throw new RuntimeException();
        return user.get();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id){
        repository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User createdUser = repository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getID())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
