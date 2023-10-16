package br.com.freitas.orders.entities.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email//(regexp = ".+@.+\\..+")
    private String email;
    private String phone;
    private String login;
    private Integer role;
}