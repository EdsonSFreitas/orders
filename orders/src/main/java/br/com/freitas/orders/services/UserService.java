package br.com.freitas.orders.services;

import br.com.freitas.orders.entities.User;
import br.com.freitas.orders.repositories.UserRepository;
import br.com.freitas.orders.services.exceptions.DatabaseException;
import br.com.freitas.orders.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User obj) {
        return repository.save(obj);
    }

    public void delete(Long id) {
        try{
            User user = findById(id);
            repository.deleteById(user.getId());
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }catch (DataIntegrityViolationException e ){
            throw new DatabaseException(e.getMessage());
        }
    }

    public User update(Long id, User obj) {
        try {
            User entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    private void updateData(User entity, User obj) {
        if (obj.getName() != null) entity.setName(obj.getName());
        if (obj.getEmail() != null) entity.setEmail(obj.getEmail());
        if (obj.getPhone() != null) entity.setPhone(obj.getPhone());
    }
}