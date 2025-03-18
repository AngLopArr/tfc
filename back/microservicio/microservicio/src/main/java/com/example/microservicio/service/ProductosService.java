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

    public ArrayList<Productos> getAllProducts(){
        return (ArrayList) productosRepository.findAll();
    }

    public boolean createProduct(Productos producto){
        Productos productoExiste = productosRepository.findByName(producto.getName());

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

    public boolean updateStockProduct(int change, String talla, Long productoId){
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