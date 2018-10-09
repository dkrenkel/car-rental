package br.com.carrental.client;

import br.com.carrental.service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "consumer", url = "${external.base.uri}")
@Component
public interface ClientFeign {

	@PostMapping("/5b97c2b42f000068007b3c59")
	ResponseEntity<Object> postExternal(@RequestBody UserDTO user);

	@GetMapping("/5b9bb9343000007b00f6b431")
	ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") Long id);

	@GetMapping("/5b9bbc903000006c00f6b44b")
	ResponseEntity<List<UserDTO>> getUsers();

}
