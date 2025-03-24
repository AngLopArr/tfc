package com.example.microservicio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.ProductosCarrito;
import com.example.microservicio.repository.ClientesRepository;
import com.example.microservicio.repository.ProductosCarritoRepository;
import com.example.microservicio.repository.ProductosRepository;

import jakarta.transaction.Transactional;

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

    public ArrayList<ProductosCarrito> getAllProductosCarritoCliente(Long idCliente){
        return (ArrayList<ProductosCarrito>) productosCarritoRepository.findProductosByClienteId(idCliente).orElse(null);
    }

    public String añadirAlCarrito(ProductosCarrito nuevoProductoCarrito){
        if(productosRepository.findById(nuevoProductoCarrito.getProducto().getId_producto()).orElse(null) != null){
            if(clientesRepository.findById(nuevoProductoCarrito.getCliente().getId_cliente()).orElse(null) != null){
                nuevoProductoCarrito.setFechaAgregado(LocalDateTime.now());
                if(List.of("S", "M", "L", "XL").contains(nuevoProductoCarrito.getTalla())){
                    if(nuevoProductoCarrito.getCantidad() > 0){
                        ProductosCarrito productoCarritoExiste = productosCarritoRepository.findByClienteIdProductoIdAndTalla(nuevoProductoCarrito.getCliente().getId_cliente(), nuevoProductoCarrito.getProducto().getId_producto(), nuevoProductoCarrito.getTalla()).orElse(null);
                        if(productoCarritoExiste != null){
                            return updateCantidad(productoCarritoExiste.getId(), nuevoProductoCarrito.getCantidad());
                        }
                        else{
                            productosService.updateStockProduct(nuevoProductoCarrito.getProducto().getId_producto(), nuevoProductoCarrito.getTalla(), -nuevoProductoCarrito.getCantidad());
                            productosCarritoRepository.save(nuevoProductoCarrito);
                            return "Producto añadido correctamente al carrito.";
                        }
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

    public String updateCantidad(Long idProductoCarrito, int cantidad){
        if(cantidad > 0){
            ProductosCarrito productoCarrito = productosCarritoRepository.findById(idProductoCarrito).orElse(null);
            if(productoCarrito != null){
                productoCarrito.setCantidad(cantidad);
                productosCarritoRepository.save(productoCarrito);
                return "El producto se ha actualizado correctamente.";
            }
            else{
                return "El producto indicado no existe.";
            }
        }
        else{ 
            return "Cantidad no válida.";
        }
    }

    public String updateTalla(Long idProductoCarrito, String tallaUpdate){
        ProductosCarrito productoCarrito = productosCarritoRepository.findById(idProductoCarrito).orElse(null);
        if(productoCarrito != null){
            if(List.of("S", "M", "L", "XL").contains(tallaUpdate)){
                ProductosCarrito productoTallaUpdate = productosCarritoRepository.findByClienteIdProductoIdAndTalla(productoCarrito.getCliente().getId_cliente(), productoCarrito.getProducto().getId_producto(), tallaUpdate).orElse(null);
                if(productoTallaUpdate != null){
                    productoTallaUpdate.setCantidad(productoTallaUpdate.getCantidad() + productoCarrito.getCantidad());
                    productosCarritoRepository.delete(productoCarrito);
                    productosCarritoRepository.save(productoTallaUpdate);
                }
                else{
                    productoCarrito.setTalla(tallaUpdate);
                    productosCarritoRepository.save(productoCarrito);
                }
                return "El producto se ha actualizado correctamente.";
            }
            else{ 
                return "Talla no válida.";
            }
        }
        else{
            return "El producto indicado no existe.";
        }
    }

    @Transactional
    public boolean eliminarProducto(Long idProductoCarrito){
        ProductosCarrito productoCarrito = productosCarritoRepository.findById(idProductoCarrito).orElse(null);
        if(productoCarrito != null){
            productosCarritoRepository.delete(productoCarrito);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean vaciarCarrito(Long clienteId){
        ArrayList<ProductosCarrito> productosCarrito = getAllProductosCarritoCliente(clienteId);
        if(productosCarrito != null){
            for (int i = 0; i < productosCarrito.size(); i++) {
                productosService.updateStockProduct(productosCarrito.get(i).getProducto().getId_producto(), productosCarrito.get(i).getTalla(), productosCarrito.get(i).getCantidad());
            }
            productosCarritoRepository.eliminarProductosDelCarrito(clienteId);
            return true;
        }
        else{
            return false;
        }
    }
}