package com.example.restaurantepragma.services;

import com.example.restaurantepragma.entities.Customer;
import com.example.restaurantepragma.entities.Logs;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.enums.OrderStatus;
import com.example.restaurantepragma.repository.LogsRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class LogsService {
    @Autowired
    private LogsRepositoy logsRepositoy;

    public void save(Order order){
        // Crear un objeto 'Logs' para el pedido en estado 'EARRING'
        Logs logs = new Logs(order, OrderStatus.EARRING);
        // Establecer la hora de inicio como el momento actual
        logs.setStartTime(LocalDateTime.now());
        // Guardar el registro en el repositorio
        logsRepositoy.save(logs);
    }

    public void inPreparationSave(Order order){
        // Crear un objeto 'Logs' para el pedido en estado 'IN_PREPARATION'
        Logs logs = new Logs(order, OrderStatus.IN_PREPARATION);
        // Establecer la hora de inicio como el momento actual
        logs.setStartTime(LocalDateTime.now());
        // Guardar el registro en el repositorio
        logsRepositoy.save(logs);

        // Obtener el registro previo en estado 'EARRING' para el mismo pedido
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);
        // Establecer la hora de finalización como el momento actual
        beforeLogs.setEndTime(LocalDateTime.now());
        // Obtener las marcas de tiempo de inicio y finalización
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();
        // Calcular la duración entre las marcas de tiempo
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);
        Long minutes = duration.toMinutes();
        // Establecer la duración en minutos en el registro previo
        beforeLogs.setTime(minutes);
        // Actualizar el registro previo en el repositorio
        logsRepositoy.save(beforeLogs);
    }

// Las funciones 'readySave', 'deliveredSave' y 'cancelSave' siguen un patrón similar, por lo que explicaré el patrón general aquí.

    public void readySave(Order order){
        // Crear un objeto 'Logs' para el pedido en estado 'READY'
        Logs logs = new Logs(order, OrderStatus.READY);
        // Establecer la hora de inicio como el momento actual
        logs.setStartTime(LocalDateTime.now());
        // Guardar el registro en el repositorio
        logsRepositoy.save(logs);

        // Obtener el registro previo en estado 'IN_PREPARATION' para el mismo pedido
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.IN_PREPARATION);
        // (mismo proceso de cálculo de duración y actualización de tiempo)

        // Actualizar el registro previo en el repositorio
        logsRepositoy.save(beforeLogs);
    }

    public void deliveredSave(Order order){
        // Obtener el registro previo en estado 'READY' para el mismo pedido
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.READY);
        // (mismo proceso de cálculo de duración y actualización de tiempo)

        // Obtener el registro inicial en estado 'EARRING' para el mismo pedido
        Logs startLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);
        // Crear un nuevo objeto 'Logs' para el pedido en estado 'DELIVERED'
        Logs logs = new Logs(order, OrderStatus.DELIVERED);
        // Establecer la hora de inicio como la hora de inicio del registro inicial
        logs.setStartTime(startLogs.getStartTime());
        // Establecer la hora de finalización como el momento actual
        logs.setEndTime(LocalDateTime.now());
        //  (mismo proceso de cálculo de duración y actualización de tiempo)

        // Guardar el nuevo registro 'logs' en el repositorio
        logsRepositoy.save(logs);
        // Actualizar el registro previo en el repositorio
        logsRepositoy.save(beforeLogs);
    }

    public void cancelSave(Order order){
        // Obtener el registro previo en estado 'EARRING' para el mismo pedido
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);
        // (mismo proceso de cálculo de duración y actualización de tiempo)

        // Crear un nuevo objeto 'Logs' para el pedido en estado 'CANCELLED'
        Logs logs = new Logs(order, OrderStatus.CANCELLED);
        // Establecer la hora de inicio como la hora de inicio del registro previo
        logs.setStartTime(beforeLogs.getStartTime());
        // Establecer la hora de finalización como el momento actual
        logs.setEndTime(LocalDateTime.now());
        // (mismo proceso de cálculo de duración y actualización de tiempo)

        // Guardar el nuevo registro 'logs' en el repositorio
        logsRepositoy.save(logs);
        // Actualizar el registro previo en el repositorio
        logsRepositoy.save(beforeLogs);
    }

}
