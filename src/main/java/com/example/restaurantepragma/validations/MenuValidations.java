package com.example.restaurantepragma.validations;

import com.example.restaurantepragma.entities.Menu;

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
                || incorrectString(menu.getCategory()) || incorrectString(menu.getFranchise()) || menu.getPreparationTime() == null);
    }
    public static boolean incorrectString(String s){
        return  s == null || s.isEmpty();
    }

}
