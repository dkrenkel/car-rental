package br.com.carrental.client;

import br.com.carrental.service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("consumer")
public interface ClientFeign {

	@PostMapping(value = "${external.post}")
	void postExternal(@RequestBody UserDTO user);
}
