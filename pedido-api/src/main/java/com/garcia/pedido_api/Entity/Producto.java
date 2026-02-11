package com.garcia.pedido_api.Entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Producto {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(nullable = false)
    private String categoria;

    private String descripcion;

    @Column(name = "precio_unit", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "image_url")
    private String imagen;
}
