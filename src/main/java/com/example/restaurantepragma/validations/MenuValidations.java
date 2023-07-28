package com.example.restaurantepragma.validations;

import com.example.restaurantepragma.entities.Menu;

import java.util.HashSet;
import java.util.Set;

public class MenuValidations {

    public static boolean rightPrice(int price){
        return price <= 0;
    }
    public static boolean validationUser(int role){return role != 1;}
    public static boolean rightPreparationTime(Double preparationTime){
        return preparationTime <= 0;
    }
    public static boolean fullFields(Menu menu){
        return ((menu.getRole() == null || menu.getRole() == 0) ||  menu.getPrice() == null ||
                incorrectString(menu.getNameMenu()) ||
                incorrectString(menu.getDescription()) || incorrectString(menu.getUrl())
                || incorrectString(menu.getCategory()) || incorrectString(menu.getCampus()) || menu.getPreparationTime() == null);
    }
    public static boolean incorrectString(String s){
        return  s == null || s.isEmpty();
    }
    public static boolean validationCategory(String category){
        Set<String> validCategory = new HashSet<>();
        validCategory.add("Típica");
        validCategory.add("Carnes");
        validCategory.add("Marina");
        validCategory.add("Postres");
        return !validCategory.contains(category);
    }
    public static boolean validationCampus(String campus){
        Set<String> validCampus = new HashSet<>();
        validCampus.add("El Tesoro");
        validCampus.add("Florida");
        validCampus.add("Bello");
        validCampus.add("Mayorca");
        return !validCampus.contains(campus);
    }

}
