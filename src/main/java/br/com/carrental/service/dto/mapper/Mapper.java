package br.com.carrental.service.dto.mapper;

import br.com.carrental.model.JPAEntity;
import br.com.carrental.service.dto.DTO;

/**
 * Interface that defines Mappers
 *
 * @author Micael
 */
public interface Mapper<E extends JPAEntity, D extends DTO> {

    /**
     * Method that maps an E that extends JPAEntity from a D that extends DTO.
     *
     * @param dto
     * @return E - E converted from an D
     */
    E map(final D dto);

    /**
     * Method that maps a D that extends DTO from an E that extends JPAEntity.
     *
     * @param entity
     * @return D - D converted from an E
     */
    D map(final E entity);
}
