package br.com.freitas.orders.services.exceptions;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 17/08/2023
 * {@code @project} courseSpringBoot
 */
public class DatabaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DatabaseException(String msg) {
        super(msg);
    }
}