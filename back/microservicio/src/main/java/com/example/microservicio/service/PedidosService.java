package com.example.microservicio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Pedido;
import com.example.microservicio.model.Pedidos;
import com.example.microservicio.model.Producto;
import com.example.microservicio.model.ProductoPedido;
import com.example.microservicio.model.ProductosPedidos;
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
        pedido.setEstado("procesando");
        return pedidosRepository.save(pedido);
    }

    public void calcularTotalPedido(Pedidos pedido){
        double total = productosPedidosRepository.calcularTotalPedido(pedido.getId_pedido());
        pedido.setTotalPedido(total);
    }

    public ArrayList<Pedido> getPedidosById(Long id){
        ArrayList<Pedidos> pedidosSinFormato = (ArrayList<Pedidos>) pedidosRepository.findPedidosByClienteId(id).orElse(null);
        ArrayList<Pedido> pedidos = new ArrayList<>();
        if(pedidosSinFormato != null){
            for (int i = 0; i < pedidosSinFormato.size(); i++) {
                pedidos.add(changePedidoToSend(pedidosSinFormato.get(i).getId_pedido()));
            }
        }
        return pedidos;
    }
    
    public Pedido changePedidoToSend(Long idPedido){
        Pedidos pedido = getPedidoById(idPedido);
        ArrayList<ProductosPedidos> productosPedidos = productosPedidosRepository.getProductosPedidos(idPedido).orElse(null);
        Pedido pedidoEnviar = new Pedido(pedido.getId_pedido(), pedido.getFechaPedido(), pedido.getTotalPedido(), pedido.getEstado());
        ArrayList<ProductoPedido> productosPedido = new ArrayList<>();
        for (int i = 0; i < productosPedidos.size(); i++) {
            ProductoPedido productoPedido = new ProductoPedido();
            productoPedido.setId(productosPedidos.get(i).getId_pedidos_productos());
            productoPedido.setProducto(new Producto(productosPedidos.get(i).getProducto().getId_producto(), productosPedidos.get(i).getProducto().getName(), productosPedidos.get(i).getProducto().getPrice(), productosPedidos.get(i).getProducto().getS(), productosPedidos.get(i).getProducto().getM(), productosPedidos.get(i).getProducto().getL(), productosPedidos.get(i).getProducto().getXL(), productosPedidos.get(i).getProducto().getImage()));
            productoPedido.setCantidad(productosPedidos.get(i).getCantidad());
            productoPedido.setTalla(productosPedidos.get(i).getTalla());
            productosPedido.add(productoPedido);
        }
        pedidoEnviar.setProductos(productosPedido);
        return pedidoEnviar;
    }
}