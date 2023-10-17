package br.com.freitas.orders.services;

import br.com.freitas.orders.entities.Order;
import br.com.freitas.orders.entities.OrderItem;
import br.com.freitas.orders.entities.Product;
import br.com.freitas.orders.entities.User;
import br.com.freitas.orders.entities.dto.OrderDTO;
import br.com.freitas.orders.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
@Service
public class OrderService {

    private final OrderRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> findAll() {
        List<Order> list = repository.findAll();
        return list.stream().map((element) -> modelMapper
                .map(element, OrderDTO.class)).toList();
    }


    public OrderDTO findById(Long id) {
        final Optional<Order> order = repository.findById(id);
        return order.map((element) -> modelMapper.map(element, OrderDTO.class))
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }


    public Order insert(Order obj, User client, Set<OrderItem> items) {
        obj.setId(null);
        obj.setClient(client);

        Set<OrderItem> orderItems = new HashSet<>();
        for (OrderItem orderItemDTO : items) {
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(obj);

            Product product = new Product();
            product.setId(orderItemDTO.getProduct().getId());
            orderItem.setProduct(product);

            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPrice(orderItemDTO.getPrice());

            orderItems.add(orderItem);
        }

        obj.setItems(orderItems);
        obj.setTotal(obj.getTotal());

        return repository.save(obj);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public OrderDTO update(Long id, OrderDTO obj) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        if (obj == null) {
            throw new IllegalArgumentException("OrderDTO cannot be null.");
        }

        Order entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));

        modelMapper.map(obj, entity);
        repository.save(entity);

        return modelMapper.map(entity, OrderDTO.class);
    }

}