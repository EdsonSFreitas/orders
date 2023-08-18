package br.com.freitas.orders.repositories;

import br.com.freitas.orders.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}