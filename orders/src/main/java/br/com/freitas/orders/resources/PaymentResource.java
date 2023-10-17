package br.com.freitas.orders.resources;

import br.com.freitas.orders.entities.Payment;
import br.com.freitas.orders.entities.dto.PaymentDTO;
import br.com.freitas.orders.entities.dto.PaymentDTOInsert;
import br.com.freitas.orders.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
@RestController
@RequestMapping("/payment")
public class PaymentResource {

    private final PaymentService service;

    public PaymentResource(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> findAll() {
        List<Payment> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> insert(@RequestBody PaymentDTOInsert dto) {

        service.save(dto.getOrderId(), dto.getMoment());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getOrderId()).toUri();

        final PaymentDTO retornoDTO = PaymentDTO.builder()
                .moment(dto.getMoment())
                .orderId(dto.getOrderId())
                .build();
        return ResponseEntity.created(uri).body(retornoDTO);
    }


    @PutMapping()
    public ResponseEntity<Payment> update(@RequestBody PaymentDTO obj) {
        final Payment updated = service.update(obj.getOrderId(), obj.getMoment());
        return ResponseEntity.ok().body(updated);
    }

}