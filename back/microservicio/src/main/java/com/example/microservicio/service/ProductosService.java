package com.example.microservicio.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Productos;
import com.example.microservicio.repository.ProductosRepository;

@Service
public class ProductosService {
    @Autowired 
    ProductosRepository productosRepository;

    public Productos getProductById(Long id){
        return productosRepository.findById(id).orElse(null);
    }

    public ArrayList<Productos> getAllProducts(){
        return (ArrayList<Productos>) productosRepository.findAll();
    }

    public boolean createProduct(Productos producto){
        Productos productoExiste = productosRepository.findByName(producto.getName()).orElse(null);

        if(productoExiste != null){
            return false;
        }
        else{
            productosRepository.save(producto);
            return productosRepository.findByName(producto.getName()) != null;
        }
    }

    public boolean updateProduct(Productos producto){
        Productos productoUpdate = productosRepository.findById(producto.getId_producto()).orElse(null);

        if(productoUpdate != null){
            productosRepository.save(producto);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteProduct(Long productoId){
        Productos productoDelete = productosRepository.findById(productoId).orElse(null);

        if(productoDelete != null){
            productosRepository.delete(productoDelete);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean updateStockProduct(Long productoId, String talla, int change){
        Productos productoUpdate = productosRepository.findById(productoId).orElse(null);

        if(productoUpdate != null){
            switch(talla){
                case "S" -> productoUpdate.setS(productoUpdate.getS() + change);
                case "M" -> productoUpdate.setM(productoUpdate.getM() + change);
                case "L" -> productoUpdate.setL(productoUpdate.getL() + change);
                case "XL" -> productoUpdate.setXL(productoUpdate.getXL() + change);
                default -> {
                    return false;
                }
            }
            productosRepository.save(productoUpdate);
            return true;
        }
        else{
            return false;
        }
    }
}

/*
DELIMITER //

CREATE PROCEDURE EliminarProductosViejosDelCarrito()
BEGIN
    DECLARE productoId INT;
    DECLARE cantidad INT;

    -- Cursor para obtener los productos del carrito que han estado por más de 24 horas
    DECLARE carrito_cursor CURSOR FOR 
        SELECT id_producto, cantidad FROM carrito WHERE TIMESTAMPDIFF(HOUR, fecha_agregado, NOW()) >= 24;

    -- Abrir el cursor
    OPEN carrito_cursor;

    -- Obtener el primer registro
    FETCH carrito_cursor INTO productoId, cantidad;

    -- Recorrer todos los productos en el carrito
    WHILE DONE = 0 DO
        -- Actualizar el stock del producto
        UPDATE productos
        SET stock = stock + cantidad
        WHERE id_producto = productoId;

        -- Obtener el siguiente producto
        FETCH carrito_cursor INTO productoId, cantidad;
    END WHILE;

    -- Cerrar el cursor
    CLOSE carrito_cursor;

    -- Eliminar los productos del carrito
    DELETE FROM carrito WHERE TIMESTAMPDIFF(HOUR, fecha_agregado, NOW()) >= 24;
END;
//

DELIMITER ;

CREATE EVENT IF NOT EXISTS eliminar_productos_viejos
ON SCHEDULE EVERY 1 DAY
DO CALL EliminarProductosViejosDelCarrito();
 */