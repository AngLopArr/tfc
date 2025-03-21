package com.example.microservicio.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.ProductosCarrito;
import com.example.microservicio.repository.ClientesRepository;
import com.example.microservicio.repository.ProductosCarritoRepository;
import com.example.microservicio.repository.ProductosRepository;

@Service
public class ProductosCarritoService {
    @Autowired 
    ProductosCarritoRepository productosCarritoRepository;

    @Autowired
    ProductosRepository productosRepository;

    @Autowired
    ProductosService productosService;

    @Autowired
    ClientesRepository clientesRepository;

    public String añadirAlCarrito(ProductosCarrito nuevoProductoCarrito){
        if(productosRepository.findById(nuevoProductoCarrito.getProducto().getId_producto()).orElse(null) != null){
            if(clientesRepository.findById(nuevoProductoCarrito.getCliente().getId_cliente()).orElse(null) != null){
                nuevoProductoCarrito.setFechaAgregado(LocalDateTime.now());
                if(List.of("S", "M", "L", "XL").contains(nuevoProductoCarrito.getTalla())){
                    if(nuevoProductoCarrito.getCantidad() > 0){
                        productosService.updateStockProduct(nuevoProductoCarrito.getProducto().getId_producto(), nuevoProductoCarrito.getTalla(), -nuevoProductoCarrito.getCantidad());
                        productosCarritoRepository.save(nuevoProductoCarrito);
                        return "Producto añadido correctamente al carrito.";
                    }
                    else{ 
                        return "La cantidad no es válida.";
                    }
                }
                else{
                    return "La talla no es válida.";
                }
            }
            else{
                return "El cliente indicado no existe.";
            }
        }
        else{ 
            return "El producto indicado no existe";
        }
    }

    public String updateCantidad(Long idCliente, Long idProducto, String talla, int cantidad){
        if(cantidad > 0){
            ProductosCarrito productoUpdate = productosCarritoRepository.findByClienteIdProductoIdAndTalla(idCliente, idProducto, talla).orElse(null);
            productoUpdate.setCantidad(cantidad);
            productosCarritoRepository.save(productoUpdate);
            return "El producto se ha actualizado correctamente.";
        }
        else{ 
            return "Cantidad no válida.";
        }
    }

    public String updateTalla(Long idCliente, Long idProducto, String tallaActual, String tallaUpdate){
        if(List.of("S", "M", "L", "XL").contains(tallaUpdate)){
            ProductosCarrito productoUpdate = productosCarritoRepository.findByClienteIdProductoIdAndTalla(idCliente, idProducto, tallaActual).orElse(null);
            ProductosCarrito productoTallaUpdate = productosCarritoRepository.findByClienteIdProductoIdAndTalla(idCliente, idProducto, tallaUpdate).orElse(null);
            if(productoTallaUpdate != null){
                productoTallaUpdate.setCantidad(productoTallaUpdate.getCantidad() + productoUpdate.getCantidad());
            }
            else{
                productoUpdate.setTalla(tallaUpdate);
            }
            productosCarritoRepository.save(productoUpdate);
            return "El producto se ha actualizado correctamente.";
        }
        else{ 
            return "Talla no válida.";
        }
    }

    public String eliminarProducto(Long idCliente, Long idProducto, String talla){
        ProductosCarrito producto = productosCarritoRepository.findByClienteIdProductoIdAndTalla(idCliente, idProducto, talla).orElse(null);
        if(producto != null){
            productosService.updateStockProduct(producto.getProducto().getId_producto(), producto.getTalla(), -producto.getCantidad());
            productosCarritoRepository.eliminarProductoDelCarritoPorClienteYProductoYTalla(idCliente, idProducto, talla);
            return "Producto eliminado del carrito con éxito.";
        }
        else{
            return "Error al encontrar el producto.";
        }
    }

    public String vaciarCarrito(Long clienteId){
        productosCarritoRepository.eliminarProductosDelCarrito(clienteId);
        return "Carrito vaciado con éxito.";
    }
}