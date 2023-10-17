package br.com.freitas.orders.entities.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

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
public class OrderDTO {
    private Long id;
    private Instant moment;
    private Integer orderStatus;
    private UserDTOInsertOrder client;
    private Set<OrderItemDTO> items;
    private PaymentDTO payment;
    private BigDecimal total;
}