package br.com.carrental.client;

import br.com.carrental.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientRest {
    private final Logger LOGGER = LoggerFactory.getLogger(ClientRest.class);

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL_MOCK = "http://www.mocky.io/v2/5b97c2b42f000068007b3c59";

    public void postOnMock(final UserDTO user){
        final ResponseEntity<UserDTO> response = restTemplate.postForEntity(URL_MOCK, user, UserDTO.class);

        LOGGER.info("m=postOnMock: Post on mock status: " +  response.getStatusCodeValue());
    }

}
