package com.garcia.pedido_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.garcia.pedido_api.Service.ProductoService;
import com.garcia.pedido_api.Util.FileStorageUtil;
import com.garcia.pedido_api.Entity.Producto;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService service;

    @GetMapping("/test")
    public String test() {
        return "API de productos funcionando correctamente";
    }

    @GetMapping
    public Page<Producto> getAll(
        @RequestParam(required = false) String search,
        @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return service.search(search, pageable);
    }
    
    @GetMapping("/{id}")
    public Producto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Producto> create(@RequestBody Producto producto) {
        Producto craer = service.create(producto);
        return ResponseEntity.status(201).body(craer);
    }

    @PostMapping("/{id}/cargar")
    public ResponseEntity<Producto> cargarImagen(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file
    ) throws Exception {
        Producto producto = service.findById(id);
        String ruta = FileStorageUtil.saveImage(file);
        producto.setImagen(ruta);
        return ResponseEntity.ok(service.update(id, producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(
        @PathVariable Long id,
        @RequestBody Producto producto
    ) {
        return ResponseEntity.ok(service.update(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
