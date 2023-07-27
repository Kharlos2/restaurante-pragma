package com.example.restaurantepragma.services;


import com.example.restaurantepragma.dto.Menu.ResponseMenuDTO;
import com.example.restaurantepragma.entities.Menu;
import com.example.restaurantepragma.maps.MenuMapper;
import com.example.restaurantepragma.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuMapper menuMapper;

    public ResponseMenuDTO save(Menu menu)throws Exception{
        try {
            if (menu.getRole()!=1){
                throw new Exception("Solo los administradores pueden crear pedidos.");
            }
            else if(menu.getPrice()<=0){
                throw new Exception("El precio tiene que ser un numero positivo y mayor a 0.");

            }
            return menuMapper.toMenuDTO(menuRepository.save(menu));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<ResponseMenuDTO> findAll() throws Exception {
        try {
            return menuMapper.toMenusDTO(menuRepository.findAll());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}