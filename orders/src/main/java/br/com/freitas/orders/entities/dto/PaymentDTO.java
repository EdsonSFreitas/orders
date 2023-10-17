package br.com.freitas.orders.entities.dto;

import lombok.*;

import java.time.Instant;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 16/10/2023
 * {@code @project} orders
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Long orderId;
    private Instant moment;
}