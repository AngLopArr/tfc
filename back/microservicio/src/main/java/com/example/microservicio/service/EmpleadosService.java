package com.example.microservicio.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.microservicio.model.Empleados;
import com.example.microservicio.repository.EmpleadosRepository;
import com.example.microservicio.util.PasswordCheck;

@Service
public class EmpleadosService {
    @Autowired 
    EmpleadosRepository empleadosRepository;

    PasswordCheck passwordCheck = new PasswordCheck();

    public Empleados login(Empleados empleado) {
        Empleados empleadoComprobar;

        if(!empleado.getEmail().equals("")){
            empleadoComprobar = getEmployeeByEmail(empleado.getEmail());
        }
        else{
            return null;
        }

        if(empleadoComprobar != null){
            if(passwordCheck.checkPassword(empleadoComprobar.getPassword(), empleado.getPassword())){ 
                return empleadoComprobar;
            }
            else{
                return null;
            }
        }
        else {
            return null;
        } 
    }

    public Empleados getEmployeeById(Long id){
        return empleadosRepository.findById(id).orElse(null);
    }

    public Empleados getEmployeeByEmail(String email){
        return empleadosRepository.findByEmail(email).orElse(null);
    }

    public ArrayList<Empleados> get8Employees(int group){
        int offset = (group * 8) - 8;
        return empleadosRepository.get8Employees(offset).orElse(null);
    }

    public ArrayList<Empleados> get8EmployeesByName(String name, int group){
        int offset = (group * 8) - 8;
        return empleadosRepository.get8EmployeesByName(name, offset).orElse(null);
    }

    public int getTotalEmployees(){
        return empleadosRepository.findAll().size();
    }

    public int getTotalEmployeesByName(String name){
        return empleadosRepository.getTotalEmployeesByName(name);
    }

    public boolean createEmployee(Empleados empleado){
        Empleados empleadoExiste = getEmployeeByEmail(empleado.getEmail());

        if(empleadoExiste != null){
            return false;
        }
        else{
            String passwordHasheada = passwordCheck.hashString(empleado.getPassword());

            empleado.setPassword(passwordHasheada);
            empleadosRepository.save(empleado);
            return getEmployeeByEmail(empleado.getEmail()) != null;
        }
    }

    public boolean updateEmployee(Empleados empleado){
        Empleados empleadoUpdate = empleadosRepository.findById(empleado.getId_empleado()).orElse(null);

        if(empleadoUpdate != null){
            String passwordHasheada = passwordCheck.hashString(empleado.getPassword());

            empleado.setPassword(passwordHasheada);
            empleadosRepository.save(empleado);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteEmployee(Long empleadoId){
        Empleados empleadoDelete = empleadosRepository.findById(empleadoId).orElse(null);

        if(empleadoDelete != null){
            empleadosRepository.delete(empleadoDelete);
            return true;
        }
        else{
            return false;
        }
    }

    public String[] getRoles(){
        String[] roles = new String[2];
        roles[0] = "admin";
        roles[1] = "employee";
        return roles;
    }
}