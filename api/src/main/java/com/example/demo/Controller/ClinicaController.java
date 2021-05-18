package com.example.demo.Controller;

import com.example.demo.Service.ClinicaService;
import com.example.demo.Entity.Clinica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClinicaController {
    @Autowired
    private ClinicaService service;

    @PostMapping(path = "/addClinica")
    public Clinica addClinica(@RequestBody Clinica clinica){
        return service.saveClinica(clinica);
    }

    @GetMapping("/clinica")
    public Clinica findClinicaByEmail(@RequestBody String email){
        return service.getClinicaByEmail(email);
    }
}