package com.example.restaurantepragma.services;

import com.example.restaurantepragma.entities.Logs;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.enums.OrderStatus;
import com.example.restaurantepragma.repository.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class LogsService {
    @Autowired
    private LogsRepository logsRepository;

    public void save(Order order){
        // Crear un objeto 'Logs' para el pedido en estado 'EARRING'
        Logs logs = new Logs(order, OrderStatus.EARRING);
        // Establecer la hora de inicio como el momento actual
        logs.setStartTime(LocalDateTime.now());
        // Guardar el registro en el repositorio
        logsRepository.save(logs);
    }

    public void inPreparationSave(Order order){
        // Crear un objeto 'Logs' para el pedido en estado 'IN_PREPARATION'
        Logs logs = new Logs(order, OrderStatus.IN_PREPARATION);
        // Establecer la hora de inicio como el momento actual
        logs.setStartTime(LocalDateTime.now());
        // Guardar el registro en el repositorio
        logsRepository.save(logs);

        // Obtener el registro previo en estado 'EARRING' para el mismo pedido
        Logs beforeLogs = logsRepository.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);
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
        logsRepository.save(beforeLogs);
    }

// Las funciones 'readySave', 'deliveredSave' y 'cancelSave' siguen un patrón similar, por lo que explicaré el patrón general aquí.

    public void readySave(Order order){
        // Crear un objeto 'Logs' para el pedido en estado 'READY'
        Logs logs = new Logs(order, OrderStatus.READY);
        // Establecer la hora de inicio como el momento actual
        logs.setStartTime(LocalDateTime.now());
        // Guardar el registro en el repositorio
        logsRepository.save(logs);

        // Obtener el registro previo en estado 'IN_PREPARATION' para el mismo pedido
        Logs beforeLogs = logsRepository.findByOrderLogIdAndStatus(order, OrderStatus.IN_PREPARATION);
        beforeLogs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);
        Long minutes = duration.toMinutes();
        beforeLogs.setTime(minutes);
        logsRepository.save(beforeLogs);
    }

    public void deliveredSave(Order order){
        // Obtener el registro previo en estado 'READY' para el mismo pedido
        Logs beforeLogs = logsRepository.findByOrderLogIdAndStatus(order, OrderStatus.READY);
        beforeLogs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);
        Long minutes = duration.toMinutes();
        beforeLogs.setTime(minutes);
        Logs startLogs = logsRepository.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);
        // Crear un nuevo objeto 'Logs' para el pedido en estado 'DELIVERED'
        Logs logs = new Logs(order, OrderStatus.DELIVERED);
        // Establecer la hora de inicio como la hora de inicio del registro inicial
        logs.setStartTime(startLogs.getStartTime());
        // Establecer la hora de finalización como el momento actual
        logs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStartFinal = startLogs.getStartTime();
        LocalDateTime dateTimeEndFinal = logs.getEndTime();
        Duration durationFinal = Duration.between(datetimeStartFinal, dateTimeEndFinal);
        Long minutesFinal = durationFinal.toMinutes();
        logs.setTime(minutesFinal);
        logsRepository.save(logs);
        // Actualizar el registro previo en el repositorio
        logsRepository.save(beforeLogs);
    }

    public void cancelSave(Order order){
        // Obtener el registro previo en estado 'EARRING' para el mismo pedido
        Logs beforeLogs = logsRepository.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);
        beforeLogs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);
        Long minutes = duration.toMinutes();
        beforeLogs.setTime(minutes);
        Logs logs = new Logs(order,OrderStatus.CANCELLED);
        logs.setStartTime(beforeLogs.getStartTime());
        // Establecer la hora de finalización como el momento actual
        logs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStartFinal = beforeLogs.getStartTime();
        LocalDateTime dateTimeEndFinal = logs.getEndTime();
        Duration durationFinal = Duration.between(datetimeStartFinal, dateTimeEndFinal);
        Long minutesFinal = durationFinal.toMinutes();
        logs.setTime(minutesFinal);
        logsRepository.save(logs);
        // Actualizar el registro previo en el repositorio
        logsRepository.save(beforeLogs);
    }

}
