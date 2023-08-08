package com.example.restaurantepragma.utils;

import com.example.restaurantepragma.enums.ClaimStatus;

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
    }    public static void sendNotificationToClaims (ClaimStatus claimStatus, String razon ) {
        try {
            // Abrir el archivo de notificaciones (o crearlo si no existe)
            FileWriter writer = new FileWriter("claims.txt", true);

            // Escribir la notificación en el archivo
            writer.write("Su orden a sido " + claimStatus + " por: " + razon + "\n");

            // Cerrar el archivo
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
