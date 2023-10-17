package br.com.freitas.orders.resources;

import br.com.freitas.orders.entities.Order;
import br.com.freitas.orders.entities.OrderItem;
import br.com.freitas.orders.entities.Product;
import br.com.freitas.orders.entities.User;
import br.com.freitas.orders.entities.dto.OrderDTO;
import br.com.freitas.orders.entities.dto.OrderItemDTO;
import br.com.freitas.orders.entities.enums.OrderStatus;
import br.com.freitas.orders.entities.pk.OrderItemPK;
import br.com.freitas.orders.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderResource {

    private final ModelMapper modelMapper;
    private final OrderService service;

    @Autowired
    private OrderResource(OrderService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@RequestBody OrderDTO orderDTO) {
        Order order = mapOrderDTOtoEntity(orderDTO);
        order = service.insert(order, order.getClient(), order.getItems());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + order.getId().toString())
                .buildAndExpand(order.getId()).toUri();

        final OrderDTO retornoDTO = OrderDTO.builder()
                .moment(orderDTO.getMoment())
                .orderStatus(orderDTO.getOrderStatus())
                .client(orderDTO.getClient())
                .items(orderDTO.getItems())
                .build();
        return ResponseEntity.created(uri).body(retornoDTO);
    }

    private Order mapOrderDTOtoEntity(OrderDTO orderDTO) {
        Order order = new Order();

        order.setMoment(orderDTO.getMoment());
        order.setOrderStatus(OrderStatus.valueOf(orderDTO.getOrderStatus()));

        User user = new User();
        user.setId(orderDTO.getClient().getId());
        order.setClient(user);

        // Crie os objetos OrderItem a partir dos OrderItemDTO
        Set<OrderItem> orderItems = new HashSet<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            OrderItem orderItem = new OrderItem();

            OrderItemPK orderItemPK = new OrderItemPK();

            orderItemPK.setOrder(order);

            Product product = new Product();
            product.setId(orderItemDTO.getProductId());
            orderItemPK.setProduct(product);

            orderItem.setId(orderItemPK);
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPrice(orderItemDTO.getPrice());

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);

        return order;
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();


    }

}