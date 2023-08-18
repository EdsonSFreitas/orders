package br.com.freitas.orders.repositories;

import br.com.freitas.orders.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}