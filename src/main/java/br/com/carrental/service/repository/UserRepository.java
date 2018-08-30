package br.com.carrental.service.repository;

import br.com.carrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Repository Interface of Users that implements JpaRepository
 *
 * @author Micael
 * */

public interface UserRepository extends JpaRepository<User, Long> {
}
