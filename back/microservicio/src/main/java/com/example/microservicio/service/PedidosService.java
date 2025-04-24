package com.example.microservicio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Pedido;
import com.example.microservicio.model.Pedidos;
import com.example.microservicio.model.Producto;
import com.example.microservicio.model.ProductoPedido;
import com.example.microservicio.repository.PedidosRepository;
import com.example.microservicio.repository.ProductosPedidosRepository;

@Service
public class PedidosService {
    @Autowired 
    PedidosRepository pedidosRepository;

    @Autowired
    ProductosPedidosRepository productosPedidosRepository;

    public Pedidos getPedidoById(Long id){
        return pedidosRepository.findById(id).orElse(null);
    }

    public Pedidos createPedidoSinTotal(Pedidos pedido){
        pedido.setFechaPedido(LocalDateTime.now());
        return pedidosRepository.save(pedido);
    }

    public void calcularTotalPedido(Pedidos pedido){
        double total = productosPedidosRepository.calcularTotalPedido(pedido.getId_pedido());
        pedido.setTotalPedido(total);
    }

    public Pedido changePedidoToSend(Long idPedido){
        Pedidos pedido = getPedidoById(idPedido);
        Pedido pedidoEnviar = new Pedido(pedido.getId_pedido(), pedido.getFechaPedido(), pedido.getTotalPedido());
        ArrayList<ProductoPedido> productosPedido = new ArrayList<>();
        for (int i = 0; i < pedido.getProductos().size(); i++) {
            ProductoPedido productoPedido = new ProductoPedido();
            productoPedido.setId(pedido.getProductos().get(i).getId_pedidos_productos());
            productoPedido.setProducto(new Producto(pedido.getProductos().get(i).getProducto().getId_producto(), pedido.getProductos().get(i).getProducto().getName(), pedido.getProductos().get(i).getProducto().getPrice(), pedido.getProductos().get(i).getProducto().getS(), pedido.getProductos().get(i).getProducto().getM(), pedido.getProductos().get(i).getProducto().getL(), pedido.getProductos().get(i).getProducto().getXL(), pedido.getProductos().get(i).getProducto().getImage()));
            productoPedido.setCantidad(pedido.getProductos().get(i).getCantidad());
            productoPedido.setTalla(pedido.getProductos().get(i).getTalla());
            productosPedido.add(productoPedido);
        }
        pedidoEnviar.setProductos(productosPedido);
        return pedidoEnviar;
    }
}