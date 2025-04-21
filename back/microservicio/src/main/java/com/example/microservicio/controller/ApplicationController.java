package com.example.microservicio.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservicio.model.Clientes;
import com.example.microservicio.model.Devolucion;
import com.example.microservicio.model.Devoluciones;
import com.example.microservicio.model.Empleados;
import com.example.microservicio.model.Pedido;
import com.example.microservicio.model.Pedidos;
import com.example.microservicio.model.Producto;
import com.example.microservicio.model.ProductoCarrito;
import com.example.microservicio.model.Productos;
import com.example.microservicio.model.ProductosCarrito;
import com.example.microservicio.model.ProductosPedidos;
import com.example.microservicio.repository.ProductosCarritoRepository;
import com.example.microservicio.service.ClientesService;
import com.example.microservicio.service.DevolucionesService;
import com.example.microservicio.service.EmpleadosService;
import com.example.microservicio.service.PedidosService;
import com.example.microservicio.service.ProductosCarritoService;
import com.example.microservicio.service.ProductosDevolucionesService;
import com.example.microservicio.service.ProductosPedidosService;
import com.example.microservicio.service.ProductosService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/aracne")
public class ApplicationController {
    @Autowired
    ClientesService clientesService;

    @Autowired
    EmpleadosService empleadosService;

    @Autowired
    ProductosService productosService;

    @Autowired
    ProductosCarritoService productosCarritoService;

    @Autowired
    PedidosService pedidosService;

    @Autowired
    ProductosPedidosService productosPedidosService;

    @Autowired
    DevolucionesService devolucionesService;

    @Autowired
    ProductosDevolucionesService productosDevolucionesService;

    @Autowired
    ProductosCarritoRepository productosCarritoRepository;

    // CLIENTES

    @GetMapping("/clientes/username/{username}")
    public ResponseEntity<Map<String, Boolean>> getClientByUsername(@PathVariable String username) {

        boolean clientExists = clientesService.getClientByUsername(username) != null;

        Map<String, Boolean> response = new HashMap<>();

        response.put("exists", clientExists);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/clientes/email/{email}")
    public ResponseEntity<Map<String, Boolean>> getClientByEmail(@PathVariable String email) {
        
        boolean clientExists = clientesService.getClientByEmail(email) != null;

        Map<String, Boolean> response = new HashMap<>();

        response.put("exists", clientExists);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/clientes/login")
    public ResponseEntity<Map<String, Object>> loginClient(@RequestBody Clientes cliente) {
        
        Clientes client = clientesService.login(cliente);

        Map<String, Object> response = new HashMap<>();

        if(client != null){
            response.put("success", true);
            response.put("id", client.getId_cliente());
            response.put("name", client.getName());
        }
        else{
            response.put("success", false);
            response.put("id", null);
            response.put("name", null);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/clientes/register")
    public ResponseEntity<Map<String, Object>> registerClient(@RequestBody Clientes cliente) {
        
        String registration = clientesService.register(cliente);

        boolean successfulRegistration = registration.equals("El registro se ha realizado correctamente.");

        Map<String, Object> response = new HashMap<>();

        response.put("success", successfulRegistration);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/clientes/password/{id}")
    public ResponseEntity<Map<String, Object>> changePasswordClient(@PathVariable Long id, @RequestBody Map<String, String> password) {
        
        String newPassword = password.get("password");

        boolean successfulChange = clientesService.changePassword(id, newPassword);

        Map<String, Object> response = new HashMap<>();

        response.put("success", successfulChange);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clientes/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCliente(@PathVariable Long id) {
        
        boolean clientExists = clientesService.deleteCliente(id);

        Map<String, Boolean> response = new HashMap<>();

        response.put("success", clientExists);

        return ResponseEntity.ok(response);
    }

    // EMPLEADOS

    @GetMapping("/employees/email/{email}")
    public ResponseEntity<Map<String, Boolean>> getEmployeeByEmail(@PathVariable String email) {
        
        boolean employeeExists = empleadosService.getEmployeeByEmail(email) != null;

        Map<String, Boolean> response = new HashMap<>();

        response.put("exists", employeeExists);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees/id/{id}")
    public ResponseEntity<Empleados> getEmployee(@PathVariable Long id) {
        
        Empleados empleado = empleadosService.getEmployeeById(id);

        if(empleado == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(empleado);
        }
    }

    @GetMapping("/employees/group/{group}")
    public ResponseEntity<ArrayList<Empleados>> get5Employees(@PathVariable int group) {
        
        ArrayList<Empleados> empleados = empleadosService.get5Employees(group);

        if(empleados.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(empleados);
        }
    }

    @GetMapping("/employees/search/{group}/{name}")
    public ResponseEntity<ArrayList<Empleados>> get5EmployeesByName(@PathVariable int group, @PathVariable String name) {
        
        ArrayList<Empleados> empleados = empleadosService.get5EmployeesByName(name, group);

        if(empleados.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(empleados);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<Map<String, Integer>> getTotalEmployees() {
        
        int total = empleadosService.getTotalEmployees();

        Map<String, Integer> response = new HashMap<>();

        response.put("total", total);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees/total/{name}")
    public ResponseEntity<Map<String, Integer>> getTotalEmployeesByName(@PathVariable String name) {
        
        int total = empleadosService.getTotalEmployeesByName(name);

        Map<String, Integer> response = new HashMap<>();

        response.put("total", total);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees/roles")
    public ResponseEntity<String[]> getEmployeesRoles() {
        
        String[] roles = empleadosService.getRoles();

        return ResponseEntity.ok(roles);
    }

    @PostMapping("/employees/login")
    public ResponseEntity<Map<String, Object>> loginEmployee(@RequestBody Empleados empleado) {
        
        Empleados employee = empleadosService.login(empleado);

        Map<String, Object> response = new HashMap<>();

        if(employee != null){
            response.put("success", true);
            response.put("role", employee.getRole());
        }
        else{
            response.put("success", false);
            response.put("role", null);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/employees/create")
    public ResponseEntity<Map<String, Boolean>> createEmployee(@RequestBody Empleados empleado) {

        boolean successfulCreation = empleadosService.createEmployee(empleado);

        Map<String, Boolean> response = new HashMap<>();

        response.put("success", successfulCreation);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/employees/update")
    public ResponseEntity<Map<String, Boolean>> updateEmployee(@RequestBody Empleados empleado) {
        
        boolean successfulUpdate = empleadosService.updateEmployee(empleado);

        Map<String, Boolean> response = new HashMap<>();

        response.put("success", successfulUpdate);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/employees/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        
        boolean successfulDeletion = empleadosService.deleteEmployee(id);

        Map<String, Boolean> response = new HashMap<>();

        response.put("success", successfulDeletion);

        return ResponseEntity.ok(response);
    }

    // PRODUCTOS

    @GetMapping("/inventory/name/{name}")
    public ResponseEntity<Map<String, Boolean>> productExists(@PathVariable String name) {
        
        Productos producto = productosService.productExists(name);

        Map<String, Boolean> response = new HashMap<>();

        if(producto != null){
            response.put("exists", true);
        }
        else{
            response.put("exists", false);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventory/id/{id}")
    public ResponseEntity<Producto> getProduct(@PathVariable Long id) {
        
        Productos producto = productosService.getProductById(id);

        if(producto == null){
            return ResponseEntity.notFound().build();
        }
        else{
            Producto productoEnviar = new Producto(producto.getId_producto(), producto.getName(), producto.getPrice(), producto.getS(), producto.getM(), producto.getL(), producto.getXL(), producto.getImage());
            return ResponseEntity.ok(productoEnviar);
        }
    }

    @GetMapping("/inventory/group/{group}")
    public ResponseEntity<ArrayList<Producto>> get4Products(@PathVariable int group) {
        
        ArrayList<Productos> productos = productosService.get4Products(group);

        if(productos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            ArrayList<Producto> productosEnviar = productosService.getProductosEnviar(productos);
            return ResponseEntity.ok(productosEnviar);
        }
    }

    @GetMapping("/inventory/search/{group}/{name}")
    public ResponseEntity<ArrayList<Producto>> get4ProductsByName(@PathVariable int group, @PathVariable String name) {
        
        ArrayList<Productos> productos = productosService.get4ProductsByName(name, group);

        if(productos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            ArrayList<Producto> productosEnviar = productosService.getProductosEnviar(productos);
            return ResponseEntity.ok(productosEnviar);
        }
    }

    @GetMapping("/inventory")
    public ResponseEntity<Map<String, Integer>> getTotalProducts() {
        
        int total = productosService.getTotalProducts();

        Map<String, Integer> response = new HashMap<>();

        response.put("total", total);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventory/total/{name}")
    public ResponseEntity<Map<String, Integer>> getTotalProductsByName(@PathVariable String name) {
        
        int total = productosService.getTotalProductsByName(name);

        Map<String, Integer> response = new HashMap<>();

        response.put("total", total);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/inventory/create")
    public ResponseEntity<Map<String, Boolean>> createProduct(@RequestBody Productos producto) {
        
        boolean successfulCreation = productosService.createProduct(producto);

        Map<String, Boolean> response = new HashMap<>();

        response.put("success", successfulCreation);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/inventory/update/stock")
    public ResponseEntity<Map<String, Boolean>> updateStockProduct(@RequestBody Map<String, Object> producto) {
        Long idProducto = (Long) producto.get("idProducto");
        String talla = (String) producto.get("talla");
        int change = (int) producto.get("change");

        boolean successfulUpdate = productosService.updateStockProduct(idProducto, talla, change);

        Map<String, Boolean> response = new HashMap<>();

        response.put("success", successfulUpdate);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/inventory/update")
    public ResponseEntity<Map<String, Boolean>> updateProduct(@RequestBody Productos producto) {
        
        boolean successfulUpdate = productosService.updateProduct(producto);

        Map<String, Boolean> response = new HashMap<>();

        response.put("success", successfulUpdate);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/inventory/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Long id) {
        
        boolean successfulDeletion = productosService.deleteProduct(id);

        Map<String, Boolean> response = new HashMap<>();

        response.put("success", successfulDeletion);

        return ResponseEntity.ok(response);
    }

    // CARRITO

    @GetMapping("/carrito/{id}")
    public ResponseEntity<ArrayList<ProductoCarrito>> getAllProductsCarritoClient(@PathVariable Long id) {
        
        ArrayList<ProductoCarrito> productosCarrito = productosCarritoService.getAllProductosCarritoCliente(id);

        if(productosCarrito.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(productosCarrito);
        }
    }

    @PostMapping("/carrito/add/{id_cliente}/{id_producto}")
    public ResponseEntity<ProductoCarrito> addProductCarritoCliente(@PathVariable Long id_cliente, @PathVariable Long id_producto, @RequestBody ProductosCarrito productoCarrito) {
        Clientes cliente = clientesService.getClientById(id_cliente);
        Productos producto = productosService.getProductById(id_producto);

        if(cliente != null){
            if(producto != null){
                productoCarrito.setCliente(cliente);
                productoCarrito.setProducto(producto);
                ProductoCarrito nuevoProducto = productosCarritoService.añadirAlCarrito(productoCarrito);
                
                if(nuevoProducto != null){
                    return ResponseEntity.ok(nuevoProducto);
                }
            }
        }

        return ResponseEntity.ok(new ProductoCarrito());
    }

    @PutMapping("/carrito/update/talla/{id}")
    public ResponseEntity<Map<String, Object>> updateTallaProductCarritoCliente(@PathVariable Long id, @RequestBody Map<String, String> talla) {
        
        String update = productosCarritoService.updateTalla(id, talla.get("talla"));

        boolean successfulUpdate = update.equals("El producto se ha actualizado correctamente.");

        Map<String, Object> response = new HashMap<>();

        response.put("success", successfulUpdate);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/carrito/update/cantidad/{id}")
    public ResponseEntity<Map<String, Object>> updateCantidadProductCarritoCliente(@PathVariable Long id, @RequestBody Map<String, Integer> cantidad) {
        
        String update = productosCarritoService.updateCantidad(id, cantidad.get("cantidad"));

        boolean successfulUpdate = update.equals("El producto se ha actualizado correctamente.");

        Map<String, Object> response = new HashMap<>();

        response.put("success", successfulUpdate);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/carrito/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProductCarritoCliente(@PathVariable Long id) {
        
        boolean successfulDeletion = productosCarritoService.eliminarProducto(id);

        Map<String, Boolean> response = new HashMap<>();

        response.put("success", successfulDeletion);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/carrito/empty/{id}")
    public ResponseEntity<Map<String, Boolean>> emptyCarritoCliente(@PathVariable Long id) {
        
        boolean successfulDeletion = productosCarritoService.vaciarCarrito(id);

        Map<String, Boolean> response = new HashMap<>();

        response.put("success", successfulDeletion);

        return ResponseEntity.ok(response);
    }

    // PEDIDOS

    @PostMapping("/pedidos/create/{id}")
    public ResponseEntity<Pedido> createPedido(@PathVariable Long id) {
        Clientes cliente = clientesService.getClientById(id);
        ArrayList<ProductosCarrito> productos = (ArrayList<ProductosCarrito>) productosCarritoRepository.findProductosByClienteId(id).orElse(null);

        if(cliente != null && productos != null){
            Pedidos pedido = new Pedidos();
            pedido.setCliente(cliente);
            pedidosService.createPedidoSinTotal(pedido);
            productosPedidosService.añadirProductosPedido(pedido, productos);
            pedidosService.calcularTotalPedido(pedido);
            Pedido pedidoEnviar = pedidosService.changePedidoToSend(pedido.getId_pedido());
            return ResponseEntity.ok(pedidoEnviar);
        }
        else{
            return ResponseEntity.ok(new Pedido());
        }
    }

    // DEVOLUCIONES

    @PostMapping("/devoluciones/create/{id_cliente}/{id_pedido}")
    public ResponseEntity<Devolucion> createDevolucion(@PathVariable Long id_cliente, @PathVariable Long id_pedido, @RequestBody ArrayList<ProductosPedidos> productos) {
        Clientes cliente = clientesService.getClientById(id_cliente);
        Pedidos pedido = pedidosService.getPedidoById(id_pedido);

        if(cliente != null && pedido != null && !productos.isEmpty() && productosPedidosService.comprobarProductosEnUnPedido(id_pedido, productos)){
            Devoluciones devolucion = new Devoluciones();
            devolucion.setCliente(cliente);
            devolucion.setPedido(pedido);
            devolucionesService.createDevolucion(devolucion);
            productosDevolucionesService.añadirProductosDevolucion(devolucion, productos);
            Devolucion devolucionEnviar = devolucionesService.changeDevolucionToSend(devolucion.getId_devolucion());
            return ResponseEntity.ok(devolucionEnviar);
        }
        else{
            return ResponseEntity.ok(new Devolucion());
        }
    }

    @PutMapping("/devoluciones/update/{id}")
    public ResponseEntity<Map<String, Object>> updateEstadoDevolucion(@PathVariable Long id, @RequestBody Map<String, Boolean> estado) {

        boolean successfulUpdate = devolucionesService.cambiarEstadoDevolucion(id, estado.get("estado"));

        Map<String, Object> response = new HashMap<>();

        response.put("success", successfulUpdate);

        return ResponseEntity.ok(response);
    }
}