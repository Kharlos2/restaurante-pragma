package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.dto.claim.ClaimDTO;
import com.example.restaurantepragma.dto.claim.ClaimErrorDTO;
import com.example.restaurantepragma.dto.claim.ClaimResquestDTO;
import com.example.restaurantepragma.dto.claim.ResponseRequestClaimDTO;
import com.example.restaurantepragma.entities.Customer;
import com.example.restaurantepragma.repository.ClaimRepository;
import com.example.restaurantepragma.enums.ClaimStatus;
import com.example.restaurantepragma.services.ClaimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Crear un nuevo reclamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reclamo creado exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - Datos de entrada inválidos",
                    content = @Content)
    })

    @PostMapping("/")
    public ResponseEntity<ClaimDTO> save(@RequestBody ClaimResquestDTO claimResquestDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(claimService.save(claimResquestDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ClaimErrorDTO(e.getMessage()));
        }
    }

    @Operation(summary = "Obtener todas las reclamaciones de pendientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exito al obtener todas las reclamaciones",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Error al obtener las reclamaciones",
                    content = @Content)
    })

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

    @Operation(summary = "Respode a un reclamo por su ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exito al obtener el reclamo",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Error al obtener el reclamo",
                    content = @Content)
    })

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
