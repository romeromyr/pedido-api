package com.garcia.pedido_api.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.garcia.pedido_api.Entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    // Se traduce como findByNombreContainingIgnoreCase SELECT *FROM producto WHERE LOWER(nombre) LIKE LOWER('%texto%')
    // pague producto(Devuelve resultados paginados) Pegeable (página, tamaño, orden)
}
