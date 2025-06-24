package com.upiiz.Examen3.servicies;

import com.upiiz.Examen3.models.ProductoModel;
import com.upiiz.Examen3.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class ProductoService implements ProductoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProductoModel> findAllProductos() {
        return jdbcTemplate.query(
                "SELECT * FROM productos",
                new BeanPropertyRowMapper<>(ProductoModel.class)
        );
    }

    @Override
    public ProductoModel findProductoById(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM productos WHERE id = ?",
                new BeanPropertyRowMapper<>(ProductoModel.class),
                id
        ).stream().findFirst().orElse(new ProductoModel());
    }

    @Override
    public ProductoModel save(ProductoModel producto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO productos(nombre, precio, stock, categoria) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, producto.getNombre());
            ps.setBigDecimal(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setString(4, producto.getCategoria());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        producto.setId(generatedId != null ? generatedId.longValue() : 0L);

        return producto;
    }

    @Override
    public int update(ProductoModel producto) {
        return jdbcTemplate.update(
                "UPDATE productos SET nombre = ?, precio = ?, stock = ?, categoria = ? WHERE id = ?",
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria(),
                producto.getId()
        );
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update(
                "DELETE FROM productos WHERE id = ?",
                id
        );
    }
}