package br.com.freitas.orders.entities;

import br.com.freitas.orders.entities.dto.UserDTO;
import br.com.freitas.orders.entities.enums.RolesEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
public class User implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private final List<Order> orders = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    private String name;
    @Email(regexp = ".+@.+\\..+")
    @NotBlank
    private String email;
    private String phone;

    @NotEmpty(message = "{field.login.obrigatorio}")
    private String login;

    @NotEmpty(message = "{field.senha.obrigatorio}")
    //@PasswordComplexity(minLength = 3, requireLowerCase = true, requireUpperCase = true, requireSpecialChar = true, requireNumber = true, message = "{field.senha.complexidade}")
    private String password;

    private Integer role;

  /*  public User() {
    }*/

/*    public User(Long id, String name, @Email String email, String phone, String password, String login, Integer role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.login = login;
        this.role = role;
    }*/

    public User(UserDTO dados) {//Classe UserDTO sem ser do tipo record
        this.id = dados.getId();
        this.name = dados.getName();
        this.email = dados.getEmail();
        this.phone = dados.getPhone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone);
    }


    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }

    public void removeOrder(Order order) {
        Optional.ofNullable(orders)
                .filter(orders -> orders.contains(order))
                .ifPresent(orders -> orders.remove(order));
    }

    public RolesEnum getRole() {
        return RolesEnum.valueOf(role);
    }

    public void setRole(RolesEnum role) {
        if(role != null) {
            this.role = role.getCode();
        }
    }
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().toString()));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}