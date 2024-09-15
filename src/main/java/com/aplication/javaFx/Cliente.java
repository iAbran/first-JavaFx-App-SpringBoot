package com.aplication.javaFx;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer numero;

    @Override
    public String toString() {
        return "ID: " + id + "   -   Nombre: " + nombre + "   -   Telefono: " + numero;
    }
}
