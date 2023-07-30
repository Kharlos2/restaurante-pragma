package com.example.restaurantepragma.validations;

import java.util.HashSet;
import java.util.Set;

public class GeneralValidations {
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
