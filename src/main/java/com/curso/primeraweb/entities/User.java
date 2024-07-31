package com.curso.primeraweb.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private boolean enabled;

    //relaciones muchos a muchos que se establece en la duena de la relacion que es usuario
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
                //si pones cascade = CascadeType.ALL indicas que si llamas la entidad user carge tambien la tabla relacionada users_roles
                //si pones fetch es para cargar las relaciones y si es Eager: La carga de los objetos de la relación se produce en el mismo momento que carga user
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    //set es una lista pero no te deja tener roles repetidos
    private Set<Role> roles = new HashSet<>();
}
