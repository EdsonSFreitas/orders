package br.com.freitas.orders.services;

import br.com.freitas.orders.entities.Order;
import br.com.freitas.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    //TODO Está inserindo com diversos campos com valor null
    public Order insert(Order obj) {
        return repository.save(obj);
    }

    //TODO Devido a integridade da chave privada não permite excluir Order
    public void delete(Long id) {
        repository.deleteById(id);
    }

    //TODO Update não está funcionando
    public Order update(Long id, Order obj) {
        Order entity = repository.getReferenceById(id);
        updateData(entity, obj);
        return repository.save(entity);
    }

    private void updateData(Order entity, Order obj) {
        entity = obj;
    }

}