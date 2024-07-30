package com.curso.primeraweb.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity // toda clase que quiera mapearse en una tabla
@Table(name="tbl_personas")//para especificar el nombre de la tabla
@Data // esta puede sustituir @Getter y @Setter
@AllArgsConstructor // CREA EL CONSTRUCTOR CON TODOS LOS CAMPOS
@NoArgsConstructor // CREA EL CONSTRUCTOR SIN LOS CAMPOS
public class Persona {

    @Id//especifica que es primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// se encarga de crear ids auto incrementable
    public long id;
    public String nombre;
    public int edad;
}