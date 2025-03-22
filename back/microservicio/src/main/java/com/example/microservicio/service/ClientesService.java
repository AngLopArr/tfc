package com.example.microservicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Clientes;
import com.example.microservicio.repository.ClientesRepository;
import com.example.microservicio.util.PasswordCheck;

@Service
public class ClientesService {
    @Autowired 
    ClientesRepository clientesRepository;

    PasswordCheck passwordCheck = new PasswordCheck();

    public Clientes login(Clientes cliente) {
        Clientes clienteComprobar;
        // Tomo el usuario correspondiente al username del usuario pasado
        if(cliente.getUsername() != null){
            clienteComprobar = getClientByUsername(cliente.getUsername());
        }
        else if (cliente.getEmail() != null){
            clienteComprobar = getClientByEmail(cliente.getEmail());
        }
        else{
            return null;
        }

        // Si este usuario existe, es decir, no es null
        if(clienteComprobar != null){
            // Empleo el método checkPassword para comprobar que la contraseña pasada coincide con la correspondiente al usuario con el que se
            // está intentando acceder y retorno true o false según el resultado
            if(passwordCheck.checkPassword(clienteComprobar.getPassword(), cliente.getPassword())){ 
                return clienteComprobar;
            }
            else{
                return null;
            }
        }
        else {
            // Si el usuario no existe, se devuelve false
            return null;
        } 
    }

    public String register(Clientes cliente) {
        Clientes emailExiste = getClientByEmail(cliente.getEmail());
        Clientes usernameExiste = getClientByUsername(cliente.getUsername());

        if(usernameExiste != null){
            return "El email indicado ya se encuentra asociado a otra cuenta.";
        }
        else if(emailExiste != null){
            return "El nombre de usuario indicado ya se encuentra asodicado a otra cuenta.";
        }
        else{
            // Hashea la contraseña antes de guardarla
            String passwordHasheada = passwordCheck.hashString(cliente.getPassword());

            // Reemplazo la contraseña por su versión hasheada
            cliente.setPassword(passwordHasheada);
            clientesRepository.save(cliente);
            return "El registro se ha realizado correctamente.";
        }
    }

    public boolean changePassword(Long id, String password){ 
        Clientes clienteUpdate = clientesRepository.findById(id).orElse(null);

        if(clienteUpdate != null){ 
            String passwordHasheada = passwordCheck.hashString(password);
            clienteUpdate.setPassword(passwordHasheada);
            clientesRepository.save(clienteUpdate);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean changeProfilePicture(Long id, String image){
        Clientes clienteUpdate = clientesRepository.findById(id).orElse(null);

        if(clienteUpdate != null){ 
            clienteUpdate.setImage(image);
            clientesRepository.save(clienteUpdate);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteCliente(Long clienteId){
        Clientes clienteDelete = clientesRepository.findById(clienteId).orElse(null);

        if(clienteDelete != null){
            clientesRepository.delete(clienteDelete);
            return true;
        }
        else{
            return false;
        }
    }

    // Método para buscar un usuario por su id
    public Clientes getClientById(Long id) {
        return clientesRepository.findById(id).orElse(null);
    }

    // Método para buscar un usuario por su nombre de usuario
    public Clientes getClientByUsername(String username) {
        return clientesRepository.findByUsername(username).orElse(null);
    }

    // Método para buscar un usuario por su email
    public Clientes getClientByEmail(String email) {
        return clientesRepository.findByEmail(email).orElse(null);
    }
}