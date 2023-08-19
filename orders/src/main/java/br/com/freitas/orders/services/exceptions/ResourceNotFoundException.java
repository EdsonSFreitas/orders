package br.com.freitas.orders.services.exceptions;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 19/08/2023
 * {@code @project} orders
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Object id) {
        super("Resource not found. Id " + id);
    }
}