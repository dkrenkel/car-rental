package br.com.carrental.service.dto.mapper;

import br.com.carrental.model.DatabaseEntity;
import br.com.carrental.service.dto.DTO;

public interface Mapper<E extends DatabaseEntity, D extends DTO> {

    /**
     * Method that maps an E that extends DatabaseEntity from a D that extends DTO.
     *
     * @param dto
     * @return User - User converted from an UserDTO
     * */
    E map (final D dto);

    /**
     * Method that maps an UserDTO from an User entity.
     *
     * @param entity
     * @return UserDTO - UserDTO converted from an User entity
     * */
    D map (final E entity);
}
