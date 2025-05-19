package com.example.microservicio.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Producto;
import com.example.microservicio.model.Productos;
import com.example.microservicio.model.ProductosCarrito;
import com.example.microservicio.model.ProductosDevoluciones;
import com.example.microservicio.model.ProductosPedidos;
import com.example.microservicio.repository.ProductosCarritoRepository;
import com.example.microservicio.repository.ProductosDevolucionesRepository;
import com.example.microservicio.repository.ProductosPedidosRepository;
import com.example.microservicio.repository.ProductosRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductosService {
    @Autowired 
    ProductosRepository productosRepository;

    @Autowired 
    ProductosCarritoRepository productosCarritoRepository;

    @Autowired 
    ProductosPedidosRepository productosPedidosRepository;

    @Autowired 
    ProductosDevolucionesRepository productosDevolucionesRepository;

    public Productos productExists(String name){
        return productosRepository.findByName(name).orElse(null);
    }

    public Productos getProductById(Long id){
        return productosRepository.findById(id).orElse(null);
    }

    public ArrayList<Productos> getProducts(int number, int group){
        int offset = (group * number) - number;
        return productosRepository.getProductos(number, offset).orElse(null);
    }

    public ArrayList<Productos> getProductsByName(String name, int number, int group){
        int offset = (group * number) - number;
        return productosRepository.getProductosByName(name, number, offset).orElse(null);
    }

    public ArrayList<Producto> getProductosEnviar(ArrayList<Productos> productos){
        ArrayList<Producto> productosEnviar = new ArrayList<>();
        for (int i = 0; i < productos.size(); i++) {
            Productos producto = productos.get(i);
            productosEnviar.add(new Producto(producto.getId_producto(), producto.getName(), producto.getPrice(), producto.getS(), producto.getM(), producto.getL(), producto.getXL(), producto.getImage()));
        }
        return productosEnviar;
    }

    public int getTotalProducts(){
        return productosRepository.findAll().size();
    }

    public int getTotalProductsByName(String name){
        return productosRepository.getTotalProductosByName(name);
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

    @Transactional
    public boolean deleteProduct(Long productoId){
        Productos productoDelete = productosRepository.findById(productoId).orElse(null);

        if(productoDelete != null){
            ArrayList<ProductosCarrito> productosCarrito = (ArrayList<ProductosCarrito>) productosCarritoRepository.getProductosByProductoId(productoId).orElse(null);
            ArrayList<ProductosPedidos> productosPedidos = (ArrayList<ProductosPedidos>) productosPedidosRepository.getProductosPedidosByProductoId(productoId).orElse(null);
            ArrayList<ProductosDevoluciones> productosDevoluciones = (ArrayList<ProductosDevoluciones>) productosDevolucionesRepository.getProductosDevolucionesByProductoId(productoId).orElse(null);

            if(productosCarrito != null){
                for (int i = 0; i < productosCarrito.size(); i++) {
                    productosCarritoRepository.delete(productosCarrito.get(i));
                }
            }

            if(productosPedidos != null){
                for (int i = 0; i < productosPedidos.size(); i++) {
                    productosPedidosRepository.delete(productosPedidos.get(i));
                }
            }

            if(productosDevoluciones != null){
                for (int i = 0; i < productosDevoluciones.size(); i++) {
                    productosDevolucionesRepository.delete(productosDevoluciones.get(i));
                }
            }
            
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