package br.com.freitas.orders.entities.dto;

import br.com.freitas.orders.entities.User;
import br.com.freitas.orders.entities.enums.RolesEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 09/10/2023
 * {@code @project} api
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDTOSaved implements Serializable {
    @Serial
    private static final long serialVersionUID = 2100395593052478548L;

    private Long id;
    @NotEmpty(message = "{field.login.obrigatorio}")
    private String login;
    private String name;
    private RolesEnum role;


    public UserDTOSaved(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.name = user.getName();
        this.role = user.getRole();
    }

    public UserDTOSaved(UserDTOSaved userDTOSaved) {
    }
}