package com.example.restaurantepragma.controllers;


import com.example.restaurantepragma.dto.menu.MenuDTO;
import com.example.restaurantepragma.dto.menu.MenuErrorDTO;
import com.example.restaurantepragma.dto.menu.MenuUpdateDTO;
import com.example.restaurantepragma.dto.menu.ResponseMenuDTO;
import com.example.restaurantepragma.entities.Menu;
import com.example.restaurantepragma.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("menus")
public class MenuController {

    // Inyección de dependencia del servicio MenuService.
    @Autowired
    private MenuService menuService;

    // Guarda un nuevo menú en la base de datos
    @PostMapping("/")
    public ResponseEntity<MenuDTO> save(
            @RequestBody Menu menu,
            @RequestParam Long employeeId,
            @RequestParam String password

    ) throws Exception{
        try {
            // Intenta guardar el menú utilizando el servicio y retorna una respuesta con el código de estado 201 (CREATED) si es exitoso.
            return ResponseEntity.status(HttpStatus.CREATED).body(menuService.save(menu, employeeId, password));
        }catch (Exception e){
            // En caso de error, retorna una respuesta con el código de estado 400 (BAD REQUEST) y un objeto MenuErrorDTO con el mensaje de error.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MenuErrorDTO(e.getMessage()));
        }
    }

    // Método para obtener todos los menús existentes mediante una solicitud GET.
    @GetMapping("/")
    public ResponseEntity<List<MenuDTO>> findAll()throws Exception{
        try {
            // Intenta obtener todos los menús utilizando el servicio y retorna una lista de menús como respuesta si es exitoso.
            return ResponseEntity.ok(new ArrayList<>(menuService.findAll()));
        }catch (Exception e){
            // En caso de error, retorna una respuesta con el código de estado 400 (BAD REQUEST) y una lista que contiene un objeto MenuErrorDTO con el mensaje de error.
            List<MenuDTO> menusDTO = new ArrayList<>();
            menusDTO.add(new MenuErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(menusDTO);
        }
    }

    // Método para buscar menús por categoría y franquicia mediante una solicitud GET.
    @GetMapping("/category/franchise/")
    public ResponseEntity<List<MenuDTO>> findForCategoryAndFranchise(
            @RequestParam String category,
            @RequestParam String franchise,
            @RequestParam() int numberRegister,
            @RequestParam() int page
    ) throws Exception{
        try{
            // Intenta buscar los menús por categoría y franquicia utilizando el servicio y retorna una lista de menús como respuesta si es exitoso.

            Page<ResponseMenuDTO> platesPages = menuService.findPlatesForCategotyAndFranchise(category,franchise,numberRegister, page);
            List<ResponseMenuDTO> plateList = platesPages.getContent();
            return ResponseEntity.ok(new ArrayList<>(plateList));
        }catch (Exception e){
            // En caso de error, retorna una respuesta con el código de estado 400 (BAD REQUEST) y una lista que contiene un objeto MenuErrorDTO con el mensaje de error.

            List<MenuDTO> menusDTO = new ArrayList<>();
            menusDTO.add(new MenuErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(menusDTO);
        }
    }

    // Método para actualizar un menú mediante una solicitud PUT utilizando su identificador (id).
    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> update(@RequestBody MenuUpdateDTO menu, @PathVariable Long id, @RequestParam Long employeeId, @RequestParam String password){
        try{
            // Intenta actualizar el menú utilizando el servicio y retorna una respuesta con el código de estado 202 (ACCEPTED) si es exitoso.

            return new  ResponseEntity<>(menuService.update(menu,id,employeeId,password),HttpStatus.ACCEPTED);
        }catch (Exception e){
            // En caso de error, retorna una respuesta con el código de estado 400 (BAD REQUEST) y un objeto MenuErrorDTO con el mensaje de error.

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MenuErrorDTO(e.getMessage()));
        }
    }

    // Método para actualizar el estado de un menú mediante una solicitud PUT utilizando su identificador (id).
    @PutMapping("/state/{id}")
    public ResponseEntity<MenuDTO> updateState(@PathVariable Long id, @RequestParam Long employeeId, @RequestParam String password ){
        try {
            // Intenta actualizar el estado del menú utilizando el servicio y retorna una respuesta con el código de estado 202 (ACCEPTED) si es exitoso.

            return new ResponseEntity<>(menuService.updateState(id,employeeId,password),HttpStatus.ACCEPTED);
        }catch (Exception e){
            // En caso de error, retorna una respuesta con el código de estado 400 (BAD REQUEST) y un objeto MenuErrorDTO con el mensaje de error.

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MenuErrorDTO(e.getMessage()));
        }
    }

}
