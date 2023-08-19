package br.com.freitas.orders.services;

import br.com.freitas.orders.entities.Category;
import br.com.freitas.orders.entities.Product;
import br.com.freitas.orders.entities.Product;
import br.com.freitas.orders.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<Product> findAll(){
        return repository.findAll();
    }

    public Product findById(Long id){
        return repository.findById(id).orElse(null);
    }

    public Product insert(Product obj) {
        return repository.save(obj);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Product update(Long id, Product obj) {
        Product entity = repository.getReferenceById(id);
        updateData(entity,obj);
        return repository.save(entity);
    }

    private void updateData(Product entity, Product obj) {
        if (obj.getName() != null) entity.setName(obj.getName());
        if (obj.getDescription() != null) entity.setDescription(obj.getDescription());
        if (obj.getPrice() != null) entity.setPrice(obj.getPrice());
        if (obj.getImgUrl() != null) entity.setImgUrl(obj.getImgUrl());
    }
}