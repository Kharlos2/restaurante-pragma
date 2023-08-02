package com.example.restaurantepragma.validations;

import java.util.*;

public class GeneralValidations {
    // Método que valida la categoría del restaurante
    // Recibe un String 'category' que representa la categoría del restaurante
    public static boolean validationCategory(String category){
        // Creamos un conjunto 'validCategory' que contiene las categorías válidas utilizando HashSet
        Set<String> validCategory = new HashSet<>(List.of("Típica","Carnes","Marina","Postres"));

        // Comprobamos si el conjunto 'validCategory' no contiene la categoría proporcionada
        // Si la categoría no está en el conjunto, entonces retorna 'true' (indicando que la categoría no es válida)
        // Si la categoría está en el conjunto, entonces retorna 'false' (indicando que la categoría es válida)
        return !validCategory.contains(category);
    }

    // Método que valida el campus del restaurante
    // Recibe un String 'campus' que representa el campus del restaurante
    public static boolean validationCampus(String campus){
        // Creamos un conjunto 'validCampus' que contiene los campus válidos utilizando HashSet
        Set<String> validCampus = new HashSet<>(List.of("El Tesoro", "Florida","Bello","Mayorca"));

        // Comprobamos si el conjunto 'validCampus' no contiene el campus proporcionado
        // Si el campus no está en el conjunto, entonces retorna 'true' (indicando que el campus no es válido)
        // Si el campus está en el conjunto, entonces retorna 'false' (indicando que el campus es válido)
        return !validCampus.contains(campus);
    }
}
