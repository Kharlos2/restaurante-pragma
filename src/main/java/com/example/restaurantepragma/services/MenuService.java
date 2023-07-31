package com.example.restaurantepragma.services;


import com.example.restaurantepragma.dto.Menu.MenuStateDTO;
import com.example.restaurantepragma.dto.Menu.MenuUpdateDTO;
import com.example.restaurantepragma.dto.Menu.ResponseMenuDTO;
import com.example.restaurantepragma.entities.Menu;
import com.example.restaurantepragma.enums.MenuResponses;
import com.example.restaurantepragma.maps.MenuMapper;
import com.example.restaurantepragma.repository.MenuRepository;
import com.example.restaurantepragma.validations.GeneralValidations;
import com.example.restaurantepragma.validations.MenuValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuMapper menuMapper;

    public ResponseMenuDTO save(Menu menu)throws Exception{
        try {
            if (MenuValidations.fullFields(menu)){
                throw new Exception(MenuResponses.EMPTY_FIELDS.getMessage());
            } else if (MenuValidations.validationUser(menu.getRole())){
                throw new Exception(MenuResponses.NO_ADMIN.getMessage());
            } else if (MenuValidations.rightPrice(menu.getPrice())){
                throw new Exception(MenuResponses.WRONG_PRICE.getMessage());
            }else if (MenuValidations.rightPreparationTime(menu.getPreparationTime())) {
                throw new Exception(MenuResponses.INCORRECT_PREPARATION_TIME.getMessage());
            }else if (GeneralValidations.validationCategory(menu.getCategory())){
                throw new Exception(MenuResponses.INCORRECT_CATEGORY.getMessage());
            } else if (GeneralValidations.validationCampus(menu.getFranchise())) {
                throw new Exception(MenuResponses.INCORRECT_FRANCHISE.getMessage());
            } else if (menuRepository.existsByNameMenuAndFranchise(menu.getNameMenu(),menu.getFranchise())) {
                throw new Exception(MenuResponses.EXISTING_PLATE.getMessage());
            }
            menu.setState(true);
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
    public ResponseMenuDTO update(MenuUpdateDTO menu, Long id, Integer user) throws Exception{
        try {
            Optional<Menu> search = menuRepository.findById(id);

            if(search.isPresent()){
                Menu menuUpdate = search.get();
                if (MenuValidations.rightPrice(menu.getPrice())){
                    throw new Exception(MenuResponses.WRONG_PRICE.getMessage());
                } else if (GeneralValidations.validationCampus(menu.getFranchise())) {
                    throw new Exception(MenuResponses.INCORRECT_FRANCHISE.getMessage());
                }else if (MenuValidations.incorrectString(menu.getDescription())){
                    throw new Exception(MenuResponses.INCORRECT_DESCRIPTION.getMessage());
                } else if (MenuValidations.validationUser(user)){
                    throw new Exception(MenuResponses.NO_ADMIN.getMessage());
                }
                menuUpdate.setPrice(menu.getPrice());
                menuUpdate.setFranchise(menu.getFranchise());
                menuUpdate.setDescription(menuUpdate.getDescription());
                return menuMapper.toMenuDTO(menuRepository.save(menuUpdate));
            }
            throw new Exception(MenuResponses.PLATE_NOT_FOUND.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public MenuStateDTO updateState(Long id, Integer user) throws Exception{
        try{
            Optional<Menu> search = menuRepository.findById(id);
            if (search.isPresent()){
                Menu update = search.get();
                if (MenuValidations.validationUser(user)){
                    throw new Exception(MenuResponses.NO_ADMIN.getMessage());
                }
                update.setState(!update.getState());
                return menuMapper.toMenuState(menuRepository.save(update));
            }
            throw new Exception(MenuResponses.PLATE_NOT_FOUND.getMessage());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public Page<ResponseMenuDTO> findPlatesForCategotyAndFranchise(String category, String franchise, int numberRegister, int page) throws Exception{
        try {
            Pageable pageable = PageRequest.of((page-1),numberRegister);
            Page<Menu> menuPage = menuRepository.findByCategoryAndFranchise(category, franchise, pageable);
            return menuPage.map(menu -> menuMapper.toMenuDTO(menu));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}