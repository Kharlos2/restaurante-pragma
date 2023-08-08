package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.dto.claim.ClaimDTO;
import com.example.restaurantepragma.dto.claim.ClaimErrorDTO;
import com.example.restaurantepragma.dto.claim.ClaimResquestDTO;
import com.example.restaurantepragma.dto.claim.ResponseRequestClaimDTO;
import com.example.restaurantepragma.enums.ClaimStatus;
import com.example.restaurantepragma.services.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("claim")
public class ClaimController {
    @Autowired
    private ClaimService claimService;
    @PostMapping("/")
    public ResponseEntity<ClaimDTO> save(@RequestBody ClaimResquestDTO claimResquestDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(claimService.save(claimResquestDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ClaimErrorDTO(e.getMessage()));
        }
    }
    @GetMapping("/findAll/earring/")
    public ResponseEntity<List<ClaimDTO>> findAllEarring(){
        try {
            return ResponseEntity.ok(new ArrayList<>(claimService.findAllEarring()));
        }catch (Exception e){
            List<ClaimDTO> claimDTOS = new ArrayList<>();
            claimDTOS.add(new ClaimErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(claimDTOS);
        }
    }

    @PutMapping("/responseClaim/")
    public ResponseEntity<ClaimDTO> responseClaim(
            @RequestBody ResponseRequestClaimDTO responseRequestClaimDTO,
            @RequestParam Long idEmpleado,
            @RequestParam String password,
            @RequestParam ClaimStatus respuesta
    ){
        try {
            return ResponseEntity.ok(claimService.responseClaim(responseRequestClaimDTO,idEmpleado,password,respuesta));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ClaimErrorDTO(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimDTO> findById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(claimService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ClaimErrorDTO(e.getMessage()));

        }
    }
}
