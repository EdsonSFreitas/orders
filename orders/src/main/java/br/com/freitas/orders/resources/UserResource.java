package br.com.freitas.orders.resources;

import br.com.freitas.orders.entities.User;
import br.com.freitas.orders.entities.dto.UserDTO;
import br.com.freitas.orders.entities.dto.UserDTOSaved;
import br.com.freitas.orders.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/users")
public class UserResource {
    int limitPageSize = 100;
    @Autowired
    private UserService service;

    @Operation(summary = "Find All by Order", description = "find all users by order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")}
    )
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAllByOrder
            (@PageableDefault(size = 20, page = 0, sort = {"id"}) Pageable pageable) {
        if (pageable.getPageSize() > limitPageSize) {
            pageable = PageRequest.of(pageable.getPageNumber(), limitPageSize, pageable.getSort());
        }

        Page<UserDTO> page = service.findAllByOrder(pageable);
        return ResponseEntity.ok().body(page);
        // http://meu.dominio.interno:8080/users?page=0&size=5&sort=email,desc
    }

    //Método mantido para fins de estudos
    @Operation(summary = "Find All", description = "find all users without pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")}
    )
    @RolesAllowed({"USER", "ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }


    @Operation(summary = "Find User by UUID with Parameter", description = "Find user by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")}
    )
    @RolesAllowed({"USER"})
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User user = service.findById(id);
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getLogin(), user.getRole().getCode());
        return ResponseEntity.ok(userDTO);
    }

    @Operation(summary = "Register new User", description = "Create a new user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "405", description = "Method Not Allowed")}
    )
    @PostMapping
    public ResponseEntity<UserDTOSaved> insert(@RequestBody @Valid User obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        UserDTOSaved objSaved = new UserDTOSaved(obj);
        return ResponseEntity.created(uri).body(objSaved);
    }


    @Operation(summary = "Update a user", description = "Update the data of an existing user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody User obj) {
        User user = service.update(id, obj);
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getLogin(), user.getRole().getCode());
        return ResponseEntity.ok().body(userDTO);
    }

    @Operation(summary = "Delete a user", description = "Delete an existing user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();


    }

}