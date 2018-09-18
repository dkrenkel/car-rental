package br.com.carrental.client;

import br.com.carrental.service.dto.UserDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientRest {
    private final Logger LOGGER = LoggerFactory.getLogger(ClientRest.class);

    private final RestTemplate restTemplate = new RestTemplate();

    private final String postUrl;
    private final String getOneUrl;
    private final String getAllUrl;

    public ClientRest(@Value("${external.post}") String postUrl,
                      @Value("${external.get.one}")String getOneUrl,
                      @Value("${external.get.all}")String getAllUrl) {
        this.postUrl = postUrl;
        this.getOneUrl = getOneUrl;
        this.getAllUrl = getAllUrl;
    }

    public void postExternal(final UserDTO user) {
        final ResponseEntity<UserDTO> response = restTemplate.postForEntity(postUrl, user, UserDTO.class);

        LOGGER.info("m=postExternal: Post on mock status: " + response.getStatusCodeValue());
    }

    public void getOneExternal() {
        //    Accept only the date pattern of the POST method
        final ResponseEntity<UserDTO> response = restTemplate.getForEntity(getOneUrl, UserDTO.class);
        LOGGER.info("m=getOneExternal: Body of what it retrieve from mock: " + response.toString());
    }

    public void getAllExternal() {
        final ResponseEntity<List<UserDTO>> response = restTemplate.exchange(getAllUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {
                });
        LOGGER.info("m=getAllExternal: Body of what it retrieve from mock: " + response.toString());
    }

}
