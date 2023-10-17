package br.com.freitas.orders.services;

import br.com.freitas.orders.entities.Payment;
import br.com.freitas.orders.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public List<Payment> findAll() {
        return repository.findAll();
    }

    public Payment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
    }


    public void save(Long orderId, Instant moment) {
        repository.insertPayment(orderId, moment);
    }


    public Payment update(Long orderId, Instant moment) {
        final Payment payment = repository.getReferenceById(orderId);
        payment.setId(orderId);
        payment.setMoment(moment);
        return repository.save(payment);
    }

}