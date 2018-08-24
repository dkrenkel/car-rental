package br.com.carrental.service.dto.mapper;

import br.com.carrental.model.User;
import br.com.carrental.service.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class UserMapper {

    public User map(final UserDTO dto) {
        return new User(dto.getIdDocument(),
                dto.getName(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getBirthDate().getTime());

    }

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
