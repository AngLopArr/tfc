package com.example.microservicio.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Acepto peticiones por parte de esta URL para que el cliente funcione
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
// Establezco la URL base de la API
@RequestMapping("/aracne")
public class ApplicationController {

}