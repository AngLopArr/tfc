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

        if(cliente.getUsername() != null){
            clienteComprobar = getClientByUsername(cliente.getUsername());
        }
        else if (cliente.getEmail() != null){
            clienteComprobar = getClientByEmail(cliente.getEmail());
        }
        else{
            return null;
        }

        if(clienteComprobar != null){
            if(passwordCheck.checkPassword(clienteComprobar.getPassword(), cliente.getPassword())){ 
                return clienteComprobar;
            }
            else{
                return null;
            }
        }
        else {
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
            String passwordHasheada = passwordCheck.hashString(cliente.getPassword());

            cliente.setPassword(passwordHasheada);
            clientesRepository.save(cliente);
            return "El registro se ha realizado correctamente.";
        }
    }

    public boolean changePassword(Long id, String passwordAnterior, String password){ 
        Clientes clienteUpdate = clientesRepository.findById(id).orElse(null);

        if(clienteUpdate != null){ 
            if(passwordCheck.checkPassword(clienteUpdate.getPassword(), passwordAnterior)){
                String passwordHasheada = passwordCheck.hashString(password);
                clienteUpdate.setPassword(passwordHasheada);
                clientesRepository.save(clienteUpdate);
                return true;
            }
            else {
                return false;
            }
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

    public Clientes getClientById(Long id) {
        return clientesRepository.findById(id).orElse(null);
    }

    public Clientes getClientByUsername(String username) {
        return clientesRepository.findByUsername(username).orElse(null);
    }

    public Clientes getClientByEmail(String email) {
        return clientesRepository.findByEmail(email).orElse(null);
    }
}