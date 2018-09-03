package br.com.carrental.service.dto.mapper;

import br.com.carrental.model.User;
import br.com.carrental.service.dto.UserDTO;
import java.util.Calendar;
import org.springframework.stereotype.Component;

/**
 * Class used to Map an User entity creation from an UserDTO and an UserDTO creation from an User entity.
 *
 * @author David
 * */

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public User map(final UserDTO dto) {
        return new User(dto.getIdDocument(),
                dto.getName(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getBirthDate().getTime());

    }

    @Override
    public UserDTO map(final User entity) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(entity.getBirthDate());

        return new UserDTO(entity.getIdDocument(),
                entity.getName(),
                entity.getEmail(),
                entity.getAddress(),
                calendar);
    }
}
