package com.example.microservicio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Producto;
import com.example.microservicio.model.ProductoCarrito;
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

    public ArrayList<ProductoCarrito> getAllProductosCarritoCliente(Long idCliente){
        ArrayList<ProductosCarrito> productos = (ArrayList<ProductosCarrito>) productosCarritoRepository.findProductosByClienteId(idCliente).orElse(null);
        ArrayList<ProductoCarrito> productosEnviar = new ArrayList<>();
        if(productos != null){
            for (int i = 0; i < productos.size(); i++) {
                productosEnviar.add(new ProductoCarrito(productos.get(i).getId(), new Producto(productos.get(i).getProducto().getId_producto(), productos.get(i).getProducto().getName(), productos.get(i).getProducto().getPrice(), productos.get(i).getProducto().getS(), productos.get(i).getProducto().getM(), productos.get(i).getProducto().getL(), productos.get(i).getProducto().getXL(), productos.get(i).getProducto().getImage()), productos.get(i).getTalla(), productos.get(i).getCantidad(), productos.get(i).getFechaAgregado()));
            }
            return productosEnviar;
        }
        else {
            return null;
        }
    }

    public ProductoCarrito añadirAlCarrito(ProductosCarrito nuevoProductoCarrito){
        if(productosRepository.findById(nuevoProductoCarrito.getProducto().getId_producto()).orElse(null) != null){
            if(clientesRepository.findById(nuevoProductoCarrito.getCliente().getId_cliente()).orElse(null) != null){
                nuevoProductoCarrito.setFechaAgregado(LocalDateTime.now());
                if(List.of("S", "M", "L", "XL").contains(nuevoProductoCarrito.getTalla())){
                    if(nuevoProductoCarrito.getCantidad() > 0){
                        ProductosCarrito productoCarritoExiste = productosCarritoRepository.findByClienteIdProductoIdAndTalla(nuevoProductoCarrito.getCliente().getId_cliente(), nuevoProductoCarrito.getProducto().getId_producto(), nuevoProductoCarrito.getTalla()).orElse(null);
                        if(productoCarritoExiste != null){
                            nuevoProductoCarrito = updateCantidad(productoCarritoExiste.getId(), productoCarritoExiste.getCantidad(), productoCarritoExiste.getCantidad() + nuevoProductoCarrito.getCantidad());
                            return new ProductoCarrito(nuevoProductoCarrito.getId(), new Producto(nuevoProductoCarrito.getProducto().getId_producto(), nuevoProductoCarrito.getProducto().getName(), nuevoProductoCarrito.getProducto().getPrice(), nuevoProductoCarrito.getProducto().getS(), nuevoProductoCarrito.getProducto().getM(), nuevoProductoCarrito.getProducto().getL(), nuevoProductoCarrito.getProducto().getXL(), nuevoProductoCarrito.getProducto().getImage()), nuevoProductoCarrito.getTalla(), nuevoProductoCarrito.getCantidad(), nuevoProductoCarrito.getFechaAgregado());
                        }
                        else{
                            productosService.updateStockProduct(nuevoProductoCarrito.getProducto().getId_producto(), nuevoProductoCarrito.getTalla(), -nuevoProductoCarrito.getCantidad());
                            nuevoProductoCarrito = productosCarritoRepository.save(nuevoProductoCarrito);
                            return new ProductoCarrito(nuevoProductoCarrito.getId(), new Producto(nuevoProductoCarrito.getProducto().getId_producto(), nuevoProductoCarrito.getProducto().getName(), nuevoProductoCarrito.getProducto().getPrice(), nuevoProductoCarrito.getProducto().getS(), nuevoProductoCarrito.getProducto().getM(), nuevoProductoCarrito.getProducto().getL(), nuevoProductoCarrito.getProducto().getXL(), nuevoProductoCarrito.getProducto().getImage()), nuevoProductoCarrito.getTalla(), nuevoProductoCarrito.getCantidad(), nuevoProductoCarrito.getFechaAgregado());
                        }
                    }
                    else{ 
                        return null;
                    }
                }
                else{
                    return null;
                }
            }
            else{
                return null;
            }
        }
        else{ 
            return null;
        }
    }

    public ProductosCarrito updateCantidad(Long idProductoCarrito, int anteriorCantidad, int cantidad){
        if(cantidad > 0){
            ProductosCarrito productoCarrito = productosCarritoRepository.findById(idProductoCarrito).orElse(null);
            if(productoCarrito != null){
                productosService.updateStockProduct(productoCarrito.getProducto().getId_producto(), productoCarrito.getTalla(), anteriorCantidad);
                productosService.updateStockProduct(productoCarrito.getProducto().getId_producto(), productoCarrito.getTalla(), -cantidad);
                productoCarrito.setCantidad(cantidad);
                return productosCarritoRepository.save(productoCarrito);
            }
            else{
                return null;
            }
        }
        else{ 
            return null;
        }
    }

    public ProductosCarrito updateTalla(Long idProductoCarrito, String anteriorTalla, String talla){
        ProductosCarrito productoCarrito = productosCarritoRepository.findById(idProductoCarrito).orElse(null);
        if(productoCarrito != null){
            if(List.of("S", "M", "L", "XL").contains(talla) && List.of("S", "M", "L", "XL").contains(anteriorTalla)){
                ProductosCarrito productoTallaUpdate = productosCarritoRepository.findByClienteIdProductoIdAndTalla(productoCarrito.getCliente().getId_cliente(), productoCarrito.getProducto().getId_producto(), talla).orElse(null);
                if(productoTallaUpdate != null){
                    productosService.updateStockProduct(productoCarrito.getProducto().getId_producto(), anteriorTalla, productoCarrito.getCantidad());
                    productosService.updateStockProduct(productoCarrito.getProducto().getId_producto(), talla, -productoCarrito.getCantidad());
                    productoTallaUpdate.setCantidad(productoTallaUpdate.getCantidad() + productoCarrito.getCantidad());
                    productosCarritoRepository.delete(productoCarrito);
                    return productosCarritoRepository.save(productoTallaUpdate);
                }
                else{
                    productoCarrito.setTalla(talla);
                    return productosCarritoRepository.save(productoCarrito);
                }
            }
            else{ 
                return null;
            }
        }
        else{
            return null;
        }
    }

    @Transactional
    public boolean eliminarProducto(Long idProductoCarrito){
        ProductosCarrito productoCarrito = productosCarritoRepository.findById(idProductoCarrito).orElse(null);
        if(productoCarrito != null){                
            productosService.updateStockProduct(productoCarrito.getProducto().getId_producto(), productoCarrito.getTalla(), productoCarrito.getCantidad());
            productosCarritoRepository.delete(productoCarrito);
            return true;
        }
        else{
            return false;
        }
    }
    
    @Transactional
    public boolean vaciarCarrito(Long clienteId){
        ArrayList<ProductosCarrito> productosCarrito = (ArrayList<ProductosCarrito>) productosCarritoRepository.findProductosByClienteId(clienteId).orElse(null);
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

    @Transactional
    public void vaciarCarritoTrasCompra(Long clienteId){
        productosCarritoRepository.eliminarProductosDelCarrito(clienteId);
    }
}