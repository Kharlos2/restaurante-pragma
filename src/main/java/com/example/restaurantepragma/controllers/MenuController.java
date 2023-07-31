package com.example.restaurantepragma.controllers;


import com.example.restaurantepragma.dto.Menu.MenuDTO;
import com.example.restaurantepragma.dto.Menu.MenuErrorDTO;
import com.example.restaurantepragma.dto.Menu.MenuUpdateDTO;
import com.example.restaurantepragma.dto.Menu.ResponseMenuDTO;
import com.example.restaurantepragma.entities.Menu;
import com.example.restaurantepragma.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("menus")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping("/")
    public ResponseEntity<MenuDTO> save(@RequestBody Menu menu) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(menuService.save(menu));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MenuErrorDTO(e.getMessage()));
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<MenuDTO>> findAll()throws Exception{
        try {
            return ResponseEntity.ok(new ArrayList<>(menuService.findAll()));
        }catch (Exception e){
            List<MenuDTO> menusDTO = new ArrayList<>();
            menusDTO.add(new MenuErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(menusDTO);
        }
    }
    @GetMapping("/category/franchise/")
    public ResponseEntity<List<MenuDTO>> findForCategoryAndFranchise(
            @RequestParam() String category,
            @RequestParam() String franchise,
            @RequestParam() int numberRegister,
            @RequestParam() int page
    ) throws Exception{
        try{
            Page<ResponseMenuDTO> platesPages = menuService.findPlatesForCategotyAndFranchise(category,franchise,numberRegister, page);
            List<ResponseMenuDTO> plateList = platesPages.getContent();
            return ResponseEntity.ok(new ArrayList<>(plateList));
        }catch (Exception e){
            List<MenuDTO> menusDTO = new ArrayList<>();
            menusDTO.add(new MenuErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(menusDTO);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> update(@RequestBody MenuUpdateDTO menu, @PathVariable Long id, @RequestParam() Integer user){
        try{
            return new  ResponseEntity<>(menuService.update(menu,id,user),HttpStatus.ACCEPTED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MenuErrorDTO(e.getMessage()));
        }
    }
    @PutMapping("/state/{id}")
    public ResponseEntity<MenuDTO> updateState(@PathVariable Long id,@RequestParam() Integer user){
        try {
            return new ResponseEntity<>(menuService.updateState(id,user),HttpStatus.ACCEPTED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MenuErrorDTO(e.getMessage()));
        }
    }

}
