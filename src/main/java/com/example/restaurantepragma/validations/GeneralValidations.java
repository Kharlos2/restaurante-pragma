package com.example.restaurantepragma.validations;

import java.util.*;

public class GeneralValidations {
    public static boolean validationCategory(String category){
        Set<String> validCategory = new HashSet<>(List.of("Típica","Carnes","Marina","Postres"));
        return !validCategory.contains(category);
    }
    public static boolean validationCampus(String campus){
        Set<String> validCampus = new HashSet<>(List.of("El Tesoro", "Florida","Bello","Mayorca"));
        return !validCampus.contains(campus);
    }
}
