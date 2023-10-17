package br.com.freitas.orders.repositories;

import br.com.freitas.orders.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {


    @Modifying
    @Query(value = "INSERT INTO tb_payment (order_id, moment) VALUES (:orderId, :moment)", nativeQuery = true)
    void insertPayment(Long orderId, Instant moment);
}