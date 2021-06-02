package com.example.demo.Controller;

import com.example.demo.Entity.Dica;
import com.example.demo.Entity.Veterinario;

import com.example.demo.Service.DicaService;
import com.example.demo.Service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class HomeController {
    @Autowired
    private VeterinarioService veterinarioService;
    @Autowired
    private DicaService dicaService;

    @CrossOrigin
    @GetMapping("/sobre-nos")
    public ResponseEntity<?> getInfo(){
        List<Veterinario> veterinario = veterinarioService.getAllVeterinarios();
        if(veterinario.size()==0){
            return ResponseEntity.badRequest().body("Não Existem Veterinários");
        }
        return  ResponseEntity.accepted().body(veterinario);
    }

    @CrossOrigin
    @GetMapping("/dicas")
    public ResponseEntity<?> getDicas(){
        List<Dica> dicas = dicaService.getAllDicas();
        if(dicas.size()==0){
            return ResponseEntity.badRequest().body("Não Existem Dicas");
        }
        return  ResponseEntity.accepted().body(dicas);
    }


}
