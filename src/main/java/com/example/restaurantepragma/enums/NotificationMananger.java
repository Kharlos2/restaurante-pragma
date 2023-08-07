package com.example.restaurantepragma.enums;

import java.io.FileWriter;
import java.io.IOException;

public class NotificationMananger {

    public static void sendNotificationToCustomer(String nombre , String message) {
        try {
            // Abrir el archivo de notificaciones (o crearlo si no existe)
            FileWriter writer = new FileWriter("notifications.txt", true);

            // Escribir la notificación en el archivo
            writer.write(nombre + ": " + message + "\n");

            // Cerrar el archivo
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
