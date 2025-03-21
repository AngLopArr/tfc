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
import com.example.microservicio.model.Empleados;
import com.example.microservicio.model.Productos;
import com.example.microservicio.service.ClientesService;
import com.example.microservicio.service.EmpleadosService;
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

    // CLIENTES

    // Empleo el método GET para comprobar la existencia de un usuario, se le pasa por parámetro el nombre del usuario a buscar
    @GetMapping("/clientes/{username}")
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
    @GetMapping("/clientes/{email}")
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
            response.put("clienteId", cliente.getId_cliente());
            response.put("imagenPerfil", cliente.getImage());
            response.put("exists", true);
        }
        else{
            response.put("clienteId", null);
            response.put("imagenPerfil", null);
            response.put("exists", false);
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
    @GetMapping("/employees/{email}")
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

    @GetMapping("/employees")
    public ResponseEntity<ArrayList<Empleados>> getAllEmployees() {
        // Almaceno la existencia del usuario en una variable boolean
        ArrayList<Empleados> empleados = empleadosService.getAllEmployees();

        // Retorno la respuesta
        return ResponseEntity.ok(empleados);
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
        Boolean successfulLogin = empleadosService.login(empleado) != null;

        // Creo un map para indicar la respuesta
        Map<String, Object> response = new HashMap<>();

        if(successfulLogin){
            response.put("role", empleado.getRole());
            response.put("exists", true);
        }
        else{
            response.put("role", null);
            response.put("exists", false);
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
        response.put("exists", successfulDeletion);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // PRODUCTOS

    @GetMapping("/inventory")
    public ResponseEntity<ArrayList<Productos>> getAllProducts() {
        // Almaceno la existencia del usuario en una variable boolean
        ArrayList<Productos> productos = productosService.getAllProducts();

        // Retorno la respuesta
        return ResponseEntity.ok(productos);
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
        response.put("exists", successfulDeletion);

        // Retorno la respuesta
        return ResponseEntity.ok(response);
    }

    // CARRITO

    // Añadir producto al carrito

    // Update talla

    // Update cantidad

    // Eliminar del carrito

    // Vaciar carrito (se tiene que devolver el stock, consulta planteada en repository)

    // PEDIDOS

    // Realizar pedidos

    // DEVOLUCIONES

    // Realizar devolucion

    // Aprobar o rechazar

    // Devolver producto al usuario (crear pedido?)
}