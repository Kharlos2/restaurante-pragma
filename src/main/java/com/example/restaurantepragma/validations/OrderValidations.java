package com.example.restaurantepragma.validations;

import java.util.List;

public class OrderValidations {

    public static String incorrectPlate(List<String> plates){
        String mensaje = "";
        if (plates.size()==1) return "El plato " +plates+" no se encuentra en esta sede.";
        return "Los platos "+plates+" no se encuentran en esta sede.";

    }
}
