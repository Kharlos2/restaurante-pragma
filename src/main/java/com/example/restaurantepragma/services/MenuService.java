package com.example.restaurantepragma.services;


import com.example.restaurantepragma.dto.menu.MenuStateDTO;
import com.example.restaurantepragma.dto.menu.MenuUpdateDTO;
import com.example.restaurantepragma.dto.menu.ResponseMenuDTO;
import com.example.restaurantepragma.entities.Employee;
import com.example.restaurantepragma.entities.Menu;
import com.example.restaurantepragma.enums.EmployeeResponses;
import com.example.restaurantepragma.enums.MenuResponses;
import com.example.restaurantepragma.maps.MenuMapper;
import com.example.restaurantepragma.repository.EmployeeRepository;
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
    @Autowired
    private EmployeeRepository employeeRepository;

    // Método para guardar un menú en la base de datos
    public ResponseMenuDTO save(Menu menu, Long employeeId, String password)throws Exception{
        try {
            // Busca el empleado por id
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            // valida si el empleado existe
            if (employee.isEmpty())throw new Exception(EmployeeResponses.NOT_FOUNT_EMPLOYEE.getMessage());
            // valida la contraseña del usuario
            else if (!employee.get().getPassword().equals(password)) throw new Exception(EmployeeResponses.INCORRECT_PASSWORD.getMessage());
            // Validar si todos los campos del menú están completos.
            else if (MenuValidations.fullFields(menu)){
                throw new Exception(MenuResponses.EMPTY_FIELDS.getMessage());
            } // Validar si el usuario que intenta crear el menú es un administrador.
            // Validar si el precio del menú es válido (mayor que cero).
            else if (MenuValidations.rightPrice(menu.getPrice())){
                throw new Exception(MenuResponses.WRONG_PRICE.getMessage());
            }// Validar si el tiempo de preparación del menú es válido (mayor que cero).
            else if (MenuValidations.rightPreparationTime(menu.getPreparationTime())) {
                throw new Exception(MenuResponses.INCORRECT_PREPARATION_TIME.getMessage());
            }// Validar si la categoría del menú es válida.
            else if (GeneralValidations.validationCategory(menu.getCategory())){
                throw new Exception(MenuResponses.INCORRECT_CATEGORY.getMessage());
            }// Validar si la franquicia del menú es válida.
            else if (GeneralValidations.validationCampus(menu.getFranchise())) {
                throw new Exception(MenuResponses.INCORRECT_FRANCHISE.getMessage());
            } // Validar si ya existe un menú con el mismo nombre y franquicia.
            else if (menuRepository.existsByNameMenuAndFranchise(menu.getNameMenu(),menu.getFranchise())) {
                throw new Exception(MenuResponses.EXISTING_PLATE.getMessage());
            }

            // Establecer el estado del menú como activo (true).
            menu.setState(true);
            return menuMapper.toMenuDTO(menuRepository.save(menu));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    // Método para obtener todos los menús existentes en la base de datos.
    public List<ResponseMenuDTO> findAll() throws Exception {
        try {
            // Se obtienen todos los menús y se mapean a DTOs para su respuesta
            return menuMapper.toMenusDTO(menuRepository.findAll());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    // Método para actualizar un menú existente en la base de datos.
    public ResponseMenuDTO update(MenuUpdateDTO menu, Long id, Long employeeId, String password) throws Exception{
        try {
            Optional<Menu> search = menuRepository.findById(id);

            if(search.isPresent()){ // Validar si el precio del menú es válido (mayor que cero).
                Menu menuUpdate = search.get();
                // Busca el empleado por id
                Optional<Employee> employee = employeeRepository.findById(employeeId);
                // valida si el empleado existe
                if (employee.isEmpty())throw new Exception(EmployeeResponses.NOT_FOUNT_EMPLOYEE.getMessage());
                    // valida la contraseña del usuario
                else if (!employee.get().getPassword().equals(password)) throw new Exception(EmployeeResponses.INCORRECT_PASSWORD.getMessage());

                else if (MenuValidations.rightPrice(menu.getPrice())){
                    throw new Exception(MenuResponses.WRONG_PRICE.getMessage());

                }// Validar si la franquicia del menú es válida.
                else if (GeneralValidations.validationCampus(menu.getFranchise())) {
                    throw new Exception(MenuResponses.INCORRECT_FRANCHISE.getMessage());

                }// Validar si la descripción del menú es válida.
                else if (MenuValidations.incorrectString(menu.getDescription())){
                    throw new Exception(MenuResponses.INCORRECT_DESCRIPTION.getMessage());

                }
                // Actualizar los campos del menú con los valores proporcionados.
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

    // Método para cambiar el estado de un menú (activar/desactivar) en la base de datos.
    public MenuStateDTO updateState(Long id, Long employeeId, String password) throws Exception{
        try{
            Optional<Menu> search = menuRepository.findById(id);
            if (search.isPresent()){
                Menu update = search.get();
                // Validar si el usuario que intenta cambiar el estado del menú es un administrador.
                Optional<Employee> employee = employeeRepository.findById(employeeId);
                // valida si el empleado existe
                if (employee.isEmpty())throw new Exception(EmployeeResponses.NOT_FOUNT_EMPLOYEE.getMessage());
                    // valida la contraseña del usuario
                else if (!employee.get().getPassword().equals(password)) throw new Exception(EmployeeResponses.INCORRECT_PASSWORD.getMessage());

                // Cambiar el estado del menú a su valor opuesto (activo/inactivo).
                update.setState(!update.getState());
                return menuMapper.toMenuState(menuRepository.save(update));
            }
            throw new Exception(MenuResponses.PLATE_NOT_FOUND.getMessage());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    // Método para obtener una lista paginada de menús según la categoría y franquicia especificadas.
    public Page<ResponseMenuDTO> findPlatesForCategotyAndFranchise(String category, String franchise, int numberRegister, int page) throws Exception{
        try {
            // Se define el objeto Pageable para la paginación y se obtienen los menús según la categoría y franquicia
            Pageable pageable = PageRequest.of((page-1),numberRegister);
            Page<Menu> menuPage = menuRepository.findByCategoryAndFranchise(category, franchise, pageable);

            // Se mapean los menús a DTOs para su respuesta
            return menuPage.map(menu -> menuMapper.toMenuDTO(menu));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}