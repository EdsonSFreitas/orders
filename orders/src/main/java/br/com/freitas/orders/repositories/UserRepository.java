package br.com.freitas.orders.repositories;

import br.com.freitas.orders.entities.User;
import br.com.freitas.orders.entities.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Page<UserDTO> findAllByOrderByName(Pageable pageable);
    Page<UserDTO> findAllByOrderByEmail(Pageable pageable);
    Page<UserDTO> findAllByOrderById(Pageable pageable);
}