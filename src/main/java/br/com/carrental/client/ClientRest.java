package br.com.carrental.client;

import br.com.carrental.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ClientRest {
	private static final String URL_MOCK = "http://www.mocky.io/v2/5b97c2b42f000068007b3c59";
	private static final String GET_ONE_URL_MOCK = "http://www.mocky.io/v2/5b9bb9343000007b00f6b431";
	private static final String GET_ALL_URL_MOCK = "http://www.mocky.io/v2/5b9bbc903000006c00f6b44b";
	private final Logger LOGGER = LoggerFactory.getLogger(ClientRest.class);
	private final RestTemplate restTemplate = new RestTemplate();

	public void postOnMock(final UserDTO user) {
		final ResponseEntity<UserDTO> response = restTemplate.postForEntity(URL_MOCK, user, UserDTO.class);

		LOGGER.info("m=postOnMock: Post on mock status: " + response.getStatusCodeValue());
	}

	public void getOneFromMock() {
		//    Accept only the date pattern of the POST method
		final ResponseEntity<UserDTO> response = restTemplate.getForEntity(GET_ONE_URL_MOCK, UserDTO.class);
		LOGGER.info("m=getOneFromMock: Body of what it retrieve from mock: " + response.toString());
	}

	public void getAllFromMock() {
		final ResponseEntity<List<UserDTO>> response = restTemplate.exchange(GET_ALL_URL_MOCK,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<UserDTO>>() {
				});
		LOGGER.info("m=getAllFromMock: Body of what it retrieve from mock: " + response.toString());
	}

}
