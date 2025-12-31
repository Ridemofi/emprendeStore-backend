package com.emprendeStore.web.controller;

import com.emprendeStore.application.service.UbicacionService;
import com.emprendeStore.web.dto.response.PaisResponseDto;
import com.emprendeStore.web.dto.response.UbicacionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicacion")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UbicacionController {
    private final UbicacionService us;

    @GetMapping("/paises")
    public ResponseEntity<List<PaisResponseDto>>listarPaises(){
        return ResponseEntity.ok(us.listarPaises());
    }

    @GetMapping("/nivel1/{idPais}")
    public ResponseEntity<List<UbicacionResponseDto>>listarUbisNivel1(@PathVariable Long idPais){
        return ResponseEntity.ok(us.listarUbisNivel1(idPais));
    }

    @GetMapping("/nivel2/{idNivel1}")
    public ResponseEntity<List<UbicacionResponseDto>>listarUbisNivel2(@PathVariable Long idNivel1){
        return ResponseEntity.ok(us.listarUbisNivel2(idNivel1));
    }

    @GetMapping("/nivel3/{idNivel2}")
    public ResponseEntity<List<UbicacionResponseDto>>listarUbisNivel3(@PathVariable Long idNivel2){
        return ResponseEntity.ok(us.listarUbisNivel3(idNivel2));
    }


}
