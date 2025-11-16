package edu.pe.upeu.delivery.controller;

import edu.pe.upeu.delivery.dto.CarritoItemDTO;
import edu.pe.upeu.delivery.service.CarritoService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "✅ Spring Boot está funcionando correctamente!";
    }

    @GetMapping("/saludo")
    public String saludo() {
        return "Hola desde tu aplicación Spring Boot 🚀";
    }
}
