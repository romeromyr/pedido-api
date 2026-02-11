package com.garcia.pedido_api.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.garcia.pedido_api.Entity.Producto;
import com.garcia.pedido_api.Repository.ProductoRepository;
import com.garcia.pedido_api.Service.ProductoService;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> search(String texto, Pageable pageable) {
        if (texto == null || texto.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.findByNombreContainingIgnoreCase(texto, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    @Transactional
    public Producto create(Producto producto) {
        producto.setId(null); // Asegura que se cree un nuevo producto
        return repository.save(producto);
    }

    @Override
    @Transactional
    public Producto update(Long id, Producto producto) {
        Producto prd = findById(id);
        prd.setNombre(producto.getNombre());
        prd.setDescripcion(producto.getDescripcion());
        prd.setPrecio(producto.getPrecio());
        prd.setImagen(producto.getImagen());

        return repository.save(prd);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
    
}
