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
import com.example.microservicio.model.Devoluciones;
import com.example.microservicio.model.Empleados;
import com.example.microservicio.model.Pedidos;
import com.example.microservicio.model.Producto;
import com.example.microservicio.model.Productos;
import com.example.microservicio.model.ProductosCarrito;
import com.example.microservicio.model.ProductosDevoluciones;
import com.example.microservicio.model.ProductosPedidos;
import com.example.microservicio.service.ClientesService;
import com.example.microservicio.service.DevolucionesService;
import com.example.microservicio.service.EmpleadosService;
import com.example.microservicio.service.PedidosService;
import com.example.microservicio.service.ProductosCarritoService;
import com.example.microservicio.service.ProductosDevolucionesService;
import com.example.microservicio.service.ProductosPedidosService;
import com.example.microservicio.service.ProductosService;

// Acepto peticiones por parte de esta URL para que el cliente funcione
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
// Establezco la URL base de la API
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

    // CLIENTES

    // Empleo el método GET para comprobar la existencia de un usuario, se le pasa por parámetro el nombre del usuario a buscar
    @GetMapping("/clientes/username/{username}")
    public ResponseEntity<Map<String, Boolean>> getClientByUsername(@PathVariable String username) {
        // Almaceno la existencia del usuario en una variable boolean
        boolean clientExists = clientesService.getClientByUsername(username) != null;

        // Creo un mapa para almacenar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Establezco que el mapa contenga una clave 'exists' y el contenido será true o false dependiendo de si el usuario existe o no
        response.put("exists", clientExists);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método GET para comprobar la existencia de un usuario, se le pasa por parámetro el nombre del usuario a buscar
    @GetMapping("/clientes/email/{email}")
    public ResponseEntity<Map<String, Boolean>> getClientByEmail(@PathVariable String email) {
        // Almaceno la existencia del usuario en una variable boolean
        boolean clientExists = clientesService.getClientByEmail(email) != null;

        // Creo un mapa para almacenar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Establezco que el mapa contenga una clave 'exists' y el contenido será true o false dependiendo de si el usuario existe o no
        response.put("exists", clientExists);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para iniciar sesión
    @PostMapping("/clientes/login")
    public ResponseEntity<Map<String, Object>> loginClient(@RequestBody Clientes cliente) {
        // Se toma el resultado de intentar hacer login con el usuario pasado
        Boolean successfulLogin = clientesService.login(cliente) != null;

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        if(successfulLogin){
            response.put("success", true);
            response.put("clienteId", cliente.getId_cliente());
            response.put("imagenPerfil", cliente.getImage());
        }
        else{
            response.put("success", false);
            response.put("clienteId", null);
            response.put("imagenPerfil", null);
        }

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para registrar un usuario
    @PostMapping("/clientes/register")
    public ResponseEntity<Map<String, Object>> registerClient(@RequestBody Clientes cliente) {
        // Se toma el resultado de intentar registrar el usuario pasado
        String registration = clientesService.register(cliente);

        // Se comprueba si el resultado ha sido exitoso
        boolean successfulRegistration = registration.equals("El registro se ha realizado correctamente.");

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulRegistration);
        response.put("content", registration);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    @PutMapping("/clientes/password/{id}")
    public ResponseEntity<Map<String, Object>> changePasswordClient(@PathVariable Long id, @RequestBody Map<String, String> password) {
        // Se toma el resultado de intentar registrar el usuario pasado
        String newPassword = password.get("password");

        // Se comprueba si el resultado ha sido exitoso
        boolean successfulChange = clientesService.changePassword(id, newPassword);

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulChange);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    @PutMapping("/clientes/picture/{id}")
    public ResponseEntity<Map<String, Object>> changeProfilePictureClient(@PathVariable Long id, @RequestBody Map<String, String> image) {
        // Se toma el resultado de intentar registrar el usuario pasado
        String newPicture = image.get("picture");

        // Se comprueba si el resultado ha sido exitoso
        boolean successfulChange = clientesService.changeProfilePicture(id, newPicture);

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulChange);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clientes/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCliente(@PathVariable Long id) {
        // Almaceno la existencia del usuario en una variable boolean
        boolean clientExists = clientesService.deleteCliente(id);

        // Creo un mapa para almacenar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Establezco que el mapa contenga una clave 'exists' y el contenido será true o false dependiendo de si el usuario existe o no
        response.put("sucess", clientExists);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // EMPLEADOS

    // Empleo el método GET para comprobar la existencia de un usuario, se le pasa por parámetro el nombre del usuario a buscar
    @GetMapping("/employees/email/{email}")
    public ResponseEntity<Map<String, Boolean>> getEmployeeByEmail(@PathVariable String email) {
        // Almaceno la existencia del usuario en una variable boolean
        boolean employeeExists = empleadosService.getEmployeeByEmail(email) != null;

        // Creo un mapa para almacenar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Establezco que el mapa contenga una clave 'exists' y el contenido será true o false dependiendo de si el usuario existe o no
        response.put("exists", employeeExists);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees/id/{id}")
    public ResponseEntity<Empleados> getEmployee(@PathVariable Long id) {
        // Almaceno la existencia del usuario en una variable boolean
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
        // Almaceno la existencia del usuario en una variable boolean
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
        // Almaceno la existencia del usuario en una variable boolean
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
        // Almaceno la existencia del usuario en una variable boolean
        int total = empleadosService.getTotalEmployees();

        Map<String, Integer> response = new HashMap<>();

        response.put("total", total);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees/total/{name}")
    public ResponseEntity<Map<String, Integer>> getTotalEmployeesByName(@PathVariable String name) {
        // Almaceno la existencia del usuario en una variable boolean
        int total = empleadosService.getTotalEmployeesByName(name);

        Map<String, Integer> response = new HashMap<>();

        response.put("total", total);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees/roles")
    public ResponseEntity<String[]> getEmployeesRoles() {
        // Almaceno la existencia del usuario en una variable boolean
        String[] roles = empleadosService.getRoles();

        // Retorno la respuesta
        return ResponseEntity.ok(roles);
    }

    // Empleo el método POST para iniciar sesión
    @PostMapping("/employees/login")
    public ResponseEntity<Map<String, Object>> loginEmployee(@RequestBody Empleados empleado) {
        // Se toma el resultado de intentar hacer login con el usuario pasado
        Empleados employee = empleadosService.login(empleado);

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        if(employee != null){
            response.put("success", true);
            response.put("role", employee.getRole());
        }
        else{
            response.put("success", false);
            response.put("role", null);
        }

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para registrar un usuario
    @PostMapping("/employees/create")
    public ResponseEntity<Map<String, Boolean>> createEmployee(@RequestBody Empleados empleado) {
        // Se comprueba si la creación ha sido exitosa
        boolean successfulCreation = empleadosService.createEmployee(empleado);

        // Creo un map para indicar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulCreation);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para registrar un usuario
    @PutMapping("/employees/update")
    public ResponseEntity<Map<String, Boolean>> updateEmployee(@RequestBody Empleados empleado) {
        // Se comprueba si la creación ha sido exitosa
        boolean successfulUpdate = empleadosService.updateEmployee(empleado);

        // Creo un map para indicar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulUpdate);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método GET para comprobar la existencia de un usuario, se le pasa por parámetro el nombre del usuario a buscar
    @DeleteMapping("/employees/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        // Almaceno la existencia del usuario en una variable boolean
        boolean successfulDeletion = empleadosService.deleteEmployee(id);

        // Creo un mapa para almacenar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Establezco que el mapa contenga una clave 'exists' y el contenido será true o false dependiendo de si el usuario existe o no
        response.put("success", successfulDeletion);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // PRODUCTOS

    @GetMapping("/inventory/name/{name}")
    public ResponseEntity<Map<String, Boolean>> productExists(@PathVariable String name) {
        // Almaceno la existencia del usuario en una variable boolean
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
    public ResponseEntity<Producto> getProducts(@PathVariable Long id) {
        // Almaceno la existencia del usuario en una variable boolean
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
        // Almaceno la existencia del usuario en una variable boolean
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
        // Almaceno la existencia del usuario en una variable boolean
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
        // Almaceno la existencia del usuario en una variable boolean
        int total = productosService.getTotalProducts();

        Map<String, Integer> response = new HashMap<>();

        response.put("total", total);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventory/total/{name}")
    public ResponseEntity<Map<String, Integer>> getTotalProductsByName(@PathVariable String name) {
        // Almaceno la existencia del usuario en una variable boolean
        int total = productosService.getTotalProductsByName(name);

        Map<String, Integer> response = new HashMap<>();

        response.put("total", total);

        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para registrar un usuario
    @PostMapping("/inventory/create")
    public ResponseEntity<Map<String, Boolean>> createProduct(@RequestBody Productos producto) {
        // Se comprueba si la creación ha sido exitosa
        boolean successfulCreation = productosService.createProduct(producto);

        // Creo un map para indicar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulCreation);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para registrar un usuario
    @PutMapping("/inventory/update/stock")
    public ResponseEntity<Map<String, Boolean>> updateStockProduct(@RequestBody Map<String, Object> producto) {
        Long idProducto = (Long) producto.get("idProducto");
        String talla = (String) producto.get("talla");
        int change = (int) producto.get("change");

        // Se comprueba si la creación ha sido exitosa
        boolean successfulUpdate = productosService.updateStockProduct(idProducto, talla, change);

        // Creo un map para indicar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulUpdate);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para registrar un usuario
    @PutMapping("/inventory/update")
    public ResponseEntity<Map<String, Boolean>> updateProduct(@RequestBody Productos producto) {
        // Se comprueba si la creación ha sido exitosa
        boolean successfulUpdate = productosService.updateProduct(producto);

        // Creo un map para indicar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulUpdate);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método GET para comprobar la existencia de un usuario, se le pasa por parámetro el nombre del usuario a buscar
    @DeleteMapping("/inventory/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Long id) {
        // Almaceno la existencia del usuario en una variable boolean
        boolean successfulDeletion = productosService.deleteProduct(id);

        // Creo un mapa para almacenar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Establezco que el mapa contenga una clave 'exists' y el contenido será true o false dependiendo de si el usuario existe o no
        response.put("success", successfulDeletion);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // CARRITO

    @GetMapping("/carrito/{id}")
    public ResponseEntity<ArrayList<ProductosCarrito>> getAllProductsCarritoClient(@PathVariable Long id) {
        // Almaceno la existencia del usuario en una variable boolean
        ArrayList<ProductosCarrito> productosCarrito = productosCarritoService.getAllProductosCarritoCliente(id);

        if(productosCarrito.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(productosCarrito);
        }
    }

    // Empleo el método POST para registrar un usuario
    @PostMapping("/carrito/add/{id_cliente}/{id_producto}")
    public ResponseEntity<Map<String, Object>> addProductCarritoCliente(@PathVariable Long id_cliente, @PathVariable Long id_producto, @RequestBody ProductosCarrito productoCarrito) {
        Clientes cliente = clientesService.getClientById(id_cliente);
        Productos producto = productosService.getProductById(id_producto);

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        if(cliente != null){
            if(producto != null){
                productoCarrito.setCliente(cliente);
                productoCarrito.setProducto(producto);
                String addition = productosCarritoService.añadirAlCarrito(productoCarrito);
                boolean successfulAddition = addition.equals("Producto añadido correctamente al carrito.");
                
                // Introduzco la respuesta en el map
                response.put("success", successfulAddition);
                response.put("content", addition);
            }
            else{
                // Introduzco la respuesta en el map
                response.put("success", false);
                response.put("content", "El producto indicado no existe.");
            }
        }
        else{
            // Introduzco la respuesta en el map
            response.put("success", false);
            response.put("content", "El cliente indicado no existe.");
        }

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para registrar un usuario
    @PutMapping("/carrito/update/talla/{id}")
    public ResponseEntity<Map<String, Object>> updateTallaProductCarritoCliente(@PathVariable Long id, @RequestBody Map<String, String> talla) {
        // Se comprueba si la creación ha sido exitosa
        String update = productosCarritoService.updateTalla(id, talla.get("talla"));

        boolean successfulUpdate = update.equals("El producto se ha actualizado correctamente.");

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulUpdate);
        response.put("content", update);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para registrar un usuario
    @PutMapping("/carrito/update/cantidad/{id}")
    public ResponseEntity<Map<String, Object>> updateCantidadProductCarritoCliente(@PathVariable Long id, @RequestBody Map<String, Integer> cantidad) {
        // Se comprueba si la creación ha sido exitosa
        String update = productosCarritoService.updateCantidad(id, cantidad.get("cantidad"));

        boolean successfulUpdate = update.equals("El producto se ha actualizado correctamente.");

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulUpdate);
        response.put("content", update);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método GET para comprobar la existencia de un usuario, se le pasa por parámetro el nombre del usuario a buscar
    @DeleteMapping("/carrito/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProductCarritoCliente(@PathVariable Long id) {
        // Almaceno la existencia del usuario en una variable boolean
        boolean successfulDeletion = productosCarritoService.eliminarProducto(id);

        // Creo un mapa para almacenar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Establezco que el mapa contenga una clave 'exists' y el contenido será true o false dependiendo de si el usuario existe o no
        response.put("success", successfulDeletion);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método GET para comprobar la existencia de un usuario, se le pasa por parámetro el nombre del usuario a buscar
    @DeleteMapping("/carrito/empty/{id}")
    public ResponseEntity<Map<String, Boolean>> emptyCarritoCliente(@PathVariable Long id) {
        // Almaceno la existencia del usuario en una variable boolean
        boolean successfulDeletion = productosCarritoService.vaciarCarrito(id);

        // Creo un mapa para almacenar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Establezco que el mapa contenga una clave 'exists' y el contenido será true o false dependiendo de si el usuario existe o no
        response.put("success", successfulDeletion);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // PEDIDOS

    // Empleo el método POST para registrar un usuario
    @PostMapping("/pedidos/create/{id}")
    public ResponseEntity<Map<String, Boolean>> createPedido(@PathVariable Long id, @RequestBody ArrayList<ProductosPedidos> productos) {
        Clientes cliente = clientesService.getClientById(id);
        boolean successfulCreation;

        if(cliente != null && !productos.isEmpty()){
            Pedidos pedido = new Pedidos();
            pedido.setCliente(cliente);
            pedidosService.createPedidoSinTotal(pedido);
            productosPedidosService.añadirProductosPedido(pedido, productos);
            pedidosService.calcularTotalPedido(pedido);
            successfulCreation  = true;
        }
        else{
            successfulCreation  = false;
        }

        // Creo un map para indicar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulCreation);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // DEVOLUCIONES
    @PostMapping("/devoluciones/create/{id_cliente}/{id_pedido}")
    public ResponseEntity<Map<String, Boolean>> createDevolucion(@PathVariable Long id_cliente, @PathVariable Long id_pedido, @RequestBody ArrayList<ProductosDevoluciones> productos) {
        Clientes cliente = clientesService.getClientById(id_cliente);
        Pedidos pedido = pedidosService.getPedidoById(id_pedido);
        boolean successfulCreation;

        if(cliente != null && pedido != null && !productos.isEmpty() && productosPedidosService.comprobarProductosEnUnPedido(id_pedido, productos)){
            Devoluciones devolucion = new Devoluciones();
            devolucion.setCliente(cliente);
            devolucion.setPedido(pedido);
            devolucionesService.createDevolucion(devolucion);
            productosDevolucionesService.añadirProductosDevolucion(devolucion, productos);
            successfulCreation  = true;
        }
        else{
            successfulCreation  = false;
        }

        // Creo un map para indicar la respuesta
        Map<String, Boolean> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulCreation);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para registrar un usuario
    @PutMapping("/devoluciones/update/{id}")
    public ResponseEntity<Map<String, Object>> updateEstadoDevolucion(@PathVariable Long id, @RequestBody Map<String, Boolean> estado) {
        boolean successfulUpdate = devolucionesService.cambiarEstadoDevolucion(id, estado.get("estado"));

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulUpdate);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // Empleo el método POST para registrar un usuario
    @PutMapping("/devoluciones/devolver/{id}")
    public ResponseEntity<Map<String, Object>> devolverProductoCliente(@PathVariable Long idDevolucion, @RequestBody Map<String, Boolean> estado) {
        boolean successfulUpdate = devolucionesService.reenviarAlCliente(idDevolucion, estado.get("estado"));
        Devoluciones devolucion = devolucionesService.getDevolucion(idDevolucion);

        if(successfulUpdate && estado.get("estado") && devolucion != null){
            Pedidos pedido = new Pedidos();
            pedido.setCliente(devolucion.getCliente());
            pedidosService.createPedidoSinTotal(pedido);
            productosPedidosService.añadirProductosPedido(pedido, productosDevolucionesService.changeProductosDevolucionToProductosPedido(idDevolucion));
            pedidosService.calcularTotalPedido(pedido);
        }

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        // Introduzco la respuesta en el map
        response.put("success", successfulUpdate);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }
}