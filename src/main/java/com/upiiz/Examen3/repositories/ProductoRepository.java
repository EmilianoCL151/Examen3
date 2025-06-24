package com.upiiz.Examen3.repositories;

import com.upiiz.Examen3.models.ProductoModel;
import java.util.List;

public interface ProductoRepository {
    List<ProductoModel> findAllProductos();
    ProductoModel findProductoById(int id);
    ProductoModel save(ProductoModel producto);
    int update(ProductoModel producto);
    int delete(int id);
}