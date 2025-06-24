package com.upiiz.Examen3.controllers;

import com.upiiz.Examen3.models.ProductoModel;
import com.upiiz.Examen3.servicies.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoModel>> obtenerTodos() {
        return ResponseEntity.ok(productoService.findAllProductos());
    }

    @PostMapping("/producto")
    public ResponseEntity<Map<String, Object>> guardarProducto(@RequestBody ProductoModel producto) {
        try {
            // Validaciones
            if (producto.getNombre() == null || producto.getNombre().length() < 3) {
                return ResponseEntity.badRequest().body(Map.of(
                        "estado", 0,
                        "mensaje", "El nombre debe tener al menos 3 caracteres"
                ));
            }

            if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body(Map.of(
                        "estado", 0,
                        "mensaje", "El precio debe ser mayor que 0"
                ));
            }

            if (producto.getStock() < 0) {
                return ResponseEntity.badRequest().body(Map.of(
                        "estado", 0,
                        "mensaje", "El stock no puede ser negativo"
                ));
            }

            if (producto.getCategoria() == null || producto.getCategoria().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "estado", 0,
                        "mensaje", "Debe seleccionar una categoría"
                ));
            }

            ProductoModel guardado = productoService.save(producto);
            if (guardado.getId() > 0) {
                return ResponseEntity.ok(Map.of(
                        "estado", 1,
                        "mensaje", "Producto guardado correctamente",
                        "producto", guardado
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                        "estado", 0,
                        "mensaje", "No se pudo guardar el producto"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                    "estado", 0,
                    "mensaje", "Error: " + e.getMessage()
            ));
        }
    }

    @PutMapping("/producto/{id}")
    public ResponseEntity<Map<String, Object>> actualizarProducto(
            @PathVariable int id, @RequestBody ProductoModel producto) {
        try {
            producto.setId((long) id);

            // Validaciones
            if (producto.getNombre() == null || producto.getNombre().length() < 3) {
                return ResponseEntity.badRequest().body(Map.of(
                        "estado", 0,
                        "mensaje", "El nombre debe tener al menos 3 caracteres"
                ));
            }

            if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body(Map.of(
                        "estado", 0,
                        "mensaje", "El precio debe ser mayor que 0"
                ));
            }

            if (producto.getStock() < 0) {
                return ResponseEntity.badRequest().body(Map.of(
                        "estado", 0,
                        "mensaje", "El stock no puede ser negativo"
                ));
            }

            if (producto.getCategoria() == null || producto.getCategoria().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "estado", 0,
                        "mensaje", "Debe seleccionar una categoría"
                ));
            }

            int filas = productoService.update(producto);
            if (filas > 0) {
                return ResponseEntity.ok(Map.of(
                        "estado", 1,
                        "mensaje", "Producto actualizado correctamente"
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                        "estado", 0,
                        "mensaje", "No se pudo actualizar el producto"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                    "estado", 0,
                    "mensaje", "Error: " + e.getMessage()
            ));
        }
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable int id) {
        try {
            int filas = productoService.delete(id);
            if (filas > 0) {
                return ResponseEntity.ok(Map.of(
                        "estado", 1,
                        "mensaje", "Producto eliminado correctamente"
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                        "estado", 0,
                        "mensaje", "No se pudo eliminar el producto"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                    "estado", 0,
                    "mensaje", "Error: " + e.getMessage()
            ));
        }
    }
}