package com.example.microservicio.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Pedidos;
import com.example.microservicio.repository.PedidosRepository;
import com.example.microservicio.repository.ProductosPedidosRepository;

@Service
public class PedidosService {
    @Autowired 
    PedidosRepository pedidosRepository;

    @Autowired
    ProductosPedidosRepository productosPedidosRepository;

    public Pedidos createPedidoSinTotal(Pedidos pedido){
        pedido.setFechaPedido(LocalDateTime.now());
        pedidosRepository.save(pedido);
        return pedido;
    }

    public String calcularTotalPedido(Pedidos pedido){
        double total = productosPedidosRepository.calcularTotalPedido(pedido.getId_pedido());
        pedido.setTotalPedido(total);
        return "El total se ha calculado correctamente.";
    }
}