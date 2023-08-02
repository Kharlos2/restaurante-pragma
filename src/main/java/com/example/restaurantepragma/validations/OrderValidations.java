package com.example.restaurantepragma.validations;

import java.util.List;

public class OrderValidations {

    // Definimos un método público y estático llamado incorrectPlate que recibe una lista de cadenas (platos).
    // La lista se representa con el tipo List<String> plates.
    public static String incorrectPlate(List<String> plates){

        // Inicializamos una cadena de texto llamada mensaje con una cadena vacía "".
        String mensaje = "";


        // Comprobamos si el tamaño de la lista (plates) es igual a 1.
        // Si es así, retornamos un mensaje que indica que el plato no se encuentra en esta sede.
        // Utilizamos el operador ternario para hacerlo de manera más concisa.
        if (plates.size()==1) return "El plato " +plates+" no se encuentra en esta sede.";

        // Si el tamaño de la lista (plates) no es igual a 1, significa que hay varios platos en la lista.
        // Retornamos un mensaje que indica que los platos no se encuentran en esta sede.
        return "Los platos "+plates+" no se encuentran en esta sede.";

    }
}
