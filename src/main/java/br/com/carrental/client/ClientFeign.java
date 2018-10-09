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

	@PostMapping("${external.post}")
	ResponseEntity<Object> postExternal(@RequestBody UserDTO user);

	@GetMapping("${external.get.one}")
	ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") Long id);

	@GetMapping("${external.get.all}")
	ResponseEntity<List<UserDTO>> getUsers();

}
