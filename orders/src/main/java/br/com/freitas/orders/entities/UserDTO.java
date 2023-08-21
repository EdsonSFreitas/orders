package br.com.freitas.orders.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class UserDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email//(regexp = ".+@.+\\..+")
    private String email;
    private String phone;

    public UserDTO(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}