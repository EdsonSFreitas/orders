package br.com.freitas.orders.services;

import br.com.freitas.orders.entities.Category;
import br.com.freitas.orders.entities.Category;
import br.com.freitas.orders.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Category insert(Category obj) {
        return repository.save(obj);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Category update(Long id, Category obj) {
        Category entity = repository.getReferenceById(id);
        updateData(entity, obj);
        return repository.save(entity);
    }

    private void updateData(Category entity, Category obj) {
        if (obj.getName() != null) entity.setName(obj.getName());
    }

}