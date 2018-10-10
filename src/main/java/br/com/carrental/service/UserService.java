package br.com.carrental.service;

import br.com.carrental.client.ClientFeign;
import br.com.carrental.client.ClientRest;
import br.com.carrental.config.CacheConfig;
import br.com.carrental.model.User;
import br.com.carrental.service.dto.UserDTO;
import br.com.carrental.service.dto.mapper.UserMapper;
import br.com.carrental.service.exception.ConstraintConflictException;
import br.com.carrental.service.exception.EntityNotFoundException;
import br.com.carrental.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class of services of the API
 *
 * @author Micael
 */

@Service
public class UserService {

	private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRepository repository;

	@Autowired
	private ClientRest clientRest;

	@Autowired
	private ClientFeign clientFeign;

	/**
	 * Method that will search the list of users in database.
	 *
	 * @return List - A List of Users, if does not exist an User in database, the list will be empty.
	 */
	@Cacheable(value = CacheConfig.CACHE_USERS)
	public List<UserDTO> getAllUsers() {
		LOGGER.info("m=getAllUsers: GET all users success");

		final List<User> list = repository.findAll();
		final List<UserDTO> listDTO = new ArrayList<>();

		for (User user : list) {
			listDTO.add(userMapper.map(user));
		}

		return listDTO;
	}

	/**
	 * Method that will search in the list of users in database for a user with a given id.
	 *
	 * @param id
	 * @return UserDTO - The User with id that was given.
	 * @throws EntityNotFoundException - When the user is not found on database
	 */
	@Cacheable(value = CacheConfig.CACHE_USER_ID)
	public UserDTO getUserById(final Long id) throws EntityNotFoundException {
		final Optional<User> user = repository.findById(id);

		if (!user.isPresent()) {
			LOGGER.warn("m=getUserById: GET user id = {} not found, ERROR: 404", id);
			throw new EntityNotFoundException();
		}

		UserDTO userDTO = userMapper.map(user.get());

		LOGGER.info("m=getUserById: GET user id = {} success", id);

		return userDTO;
	}

	/**
	 * Method that will search in the list of users in database for a user with a given id and delete it.
	 *
	 * @param id
	 * @throws EntityNotFoundException - When the user is not found on database
	 */
	@Caching(evict = {
			@CacheEvict(value = CacheConfig.CACHE_USER_ID),
			@CacheEvict(value = CacheConfig.CACHE_USERS, allEntries = true)
	})
	//If an user will be deleted, the two caches will be reseted.
	public void deleteUserById(final Long id) throws EntityNotFoundException {

		if (!repository.findById(id).isPresent()) {
			LOGGER.warn("m=deleteUserById: DELETE user id = {} not found, ERROR:404", id);
			throw new EntityNotFoundException();
		}

		repository.deleteById(id);

		LOGGER.info("m=deleteUserById: DELETE user id = {} success", id);
	}

	/**
	 * Method that will receive an UserDTO and save it on database.
	 *
	 * @param user
	 * @return Long - The id of the User that was given after the save on database.
	 * @throws ConstraintConflictException - When any constraint is violated. In this case, the oneness of idDocument and/or email.
	 */
	@TransactionalEventListener
	@CacheEvict(value = CacheConfig.CACHE_USERS, allEntries = true)
	//If an user will be created, the cache of Users will be reseted.
	public Long saveUser(final UserDTO user) throws ConstraintConflictException {
		final User createdUser;

		//verification if the user was already created
		try {
			createdUser = repository
					.save(userMapper.map(user));

			clientRest.postExternal(user);
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("m=saveUser: POST user idDocument = {} has conflict, ERROR:409", user.getIdDocument());
			throw new ConstraintConflictException();
		}

		LOGGER.info("m=saveUser: POST user idDocument = {} success", createdUser.getIdDocument());

		return createdUser.getId();
	}
}
