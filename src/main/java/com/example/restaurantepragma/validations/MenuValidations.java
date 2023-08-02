package com.example.restaurantepragma.validations;

import com.example.restaurantepragma.entities.Menu;

// Método que verifica si el precio es correcto (mayor a cero).
public class MenuValidations {

    // Método que valida si el rol del usuario es correcto (diferente de 1).
    public static boolean rightPrice(int price){
        return price <= 0;
    }
    // Método que valida si el rol del usuario es correcto (diferente de 1).
    public static boolean validationUser(int role){return role != 1;}

    // Método que verifica si el tiempo de preparación es correcto (mayor a cero).
    public static boolean rightPreparationTime(Double preparationTime){
        return preparationTime <= 0;
    }

    // Método que verifica si todos los campos del menú están completos.
    public static boolean fullFields(Menu menu){
        return ((menu.getRole() == null || menu.getRole() == 0) ||  menu.getPrice() == null ||
                incorrectString(menu.getNameMenu()) ||
                incorrectString(menu.getDescription()) || incorrectString(menu.getUrl())
                || incorrectString(menu.getCategory()) || incorrectString(menu.getFranchise()) || menu.getPreparationTime() == null);
    }

    // Método que verifica si una cadena (String) es incorrecta (nula o vacía).
    public static boolean incorrectString(String s){
        return  s == null || s.isEmpty();
    }

}
