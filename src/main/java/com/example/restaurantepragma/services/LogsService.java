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
    private LogsRepository logsRepositoy;



    public void save(Order order){

        // Crea un nuevo objeto logs con el estado de EARRING y la orden proporcionada
        Logs logs = new Logs(order,OrderStatus.EARRING);

        // Establecer la hora de inicio actual en el objeto Logs.
        logs.setStartTime(LocalDateTime.now());

        // Guardar el objeto Logs en el repositorio
        logsRepositoy.save(logs);
    }
    public void inPreparationSave(Order order){

        // Crear un nuevo objeto Logs con el estado IN_PREPARATION y la orden proporcionada.
        Logs logs = new Logs(order, OrderStatus.IN_PREPARATION);

        // Establecer la hora de inicio actual en el objeto Logs.
        logs.setStartTime(LocalDateTime.now());

        // Guardar el objeto Logs en el repositorio.
        logsRepositoy.save(logs);

        // Buscar los registros anteriores con estado EARRING para la orden dada.
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);

        // Establecer la hora de finalización actual en los registros anteriores.
        beforeLogs.setEndTime(LocalDateTime.now());

        // Obtener la hora de inicio y finalización de los registros anteriores.
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();

        // Calcular la duración en minutos entre la hora de inicio y finalización.
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);
        Long minutes = duration.toMinutes();

        // Establecer el tiempo (duración en minutos) en los registros anteriores.
        beforeLogs.setTime(minutes);

        // Guardar los registros anteriores actualizados en el repositorio.
        logsRepositoy.save(beforeLogs);
    }
    public void readySave(Order order) {
        // Crear una instancia de Logs con el estado "READY" y la orden dada
        Logs logs = new Logs(order, OrderStatus.READY);

        // Establecer la hora de inicio como el momento actual
        logs.setStartTime(LocalDateTime.now());

        // Guardar el objeto logs en el repositorio
        logsRepositoy.save(logs);

        // Buscar los registros de Logs con el estado "IN_PREPARATION" relacionados con la misma orden
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.IN_PREPARATION);

        // Establecer la hora de finalización como el momento actual
        beforeLogs.setEndTime(LocalDateTime.now());

        // Obtener las marcas de tiempo de inicio y finalización
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();

        // Calcular la duración entre las marcas de tiempo
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);

        // Obtener la duración en minutos
        Long minutes = duration.toMinutes();

        // Establecer la duración en minutos en el objeto beforeLogs
        beforeLogs.setTime(minutes);

        // Guardar los cambios en el objeto beforeLogs en el repositorio
        logsRepositoy.save(beforeLogs);
    }

    public void deliveredSave(Order order){

        // Buscar registros de logs antes de la entrega que estén en estado "READY"
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.READY);

        // Establecer la hora de finalización de los logs antes de la entrega como el tiempo actual
        beforeLogs.setEndTime(LocalDateTime.now());

        // Obtener la hora de inicio y finalización de los logs antes de la entrega
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();

        // Calcular la duración entre la hora de inicio y finalización
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);

        // Convertir la duración a minutos
        Long minutes = duration.toMinutes();

        // Establecer el tiempo transcurrido en minutos en los logs antes de la entrega
        beforeLogs.setTime(minutes);

        // Buscar registros de logs al inicio del proceso de entrega que estén en estado "EARRING"
        Logs startLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);

        // Crear nuevos registros de logs para marcar la entrega como completada ("DELIVERED")
        Logs logs = new Logs(order, OrderStatus.DELIVERED);

        // Establecer la hora de inicio de los nuevos logs como la hora de inicio de los logs al inicio del proceso de entrega
        logs.setStartTime(startLogs.getStartTime());

        // Establecer la hora de finalización de los nuevos logs como el tiempo actual
        logs.setEndTime(LocalDateTime.now());

        // Obtener la hora de inicio y finalización de los nuevos logs
        LocalDateTime datetimeStartFinal = startLogs.getStartTime();
        LocalDateTime dateTimeEndFinal = logs.getEndTime();

        // Calcular la duración entre la hora de inicio y finalización de los nuevos logs
        Duration durationFinal = Duration.between(datetimeStartFinal, dateTimeEndFinal);

        // Convertir la duración a minutos
        Long minutesFinal = durationFinal.toMinutes();

        // Establecer el tiempo transcurrido en minutos en los nuevos logs de entrega
        logs.setTime(minutesFinal);

        // Guardar los nuevos registros de logs de entrega en el repositorio
        logsRepositoy.save(logs);

        // Guardar los cambios en los registros de logs antes de la entrega en el repositorio
        logsRepositoy.save(beforeLogs);
}
    public void cancelSave(Order order){

        // Buscar los registros de logs correspondientes a la orden con el estado "EARRING"
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);

        // Registrar la hora de finalización en los registros "beforeLogs"
        beforeLogs.setEndTime(LocalDateTime.now());

        // Obtener la fecha y hora de inicio de los registros "beforeLogs"
        LocalDateTime datetimeStart = beforeLogs.getStartTime();

        // Obtener la fecha y hora de finalización de los registros "beforeLogs"
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();

        // Calcular la duración entre la fecha y hora de inicio y la fecha y hora de finalización
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);

        // Convertir la duración a minutos
        Long minutes = duration.toMinutes();

        // Establecer el tiempo transcurrido en minutos en los registros "beforeLogs"
        beforeLogs.setTime(minutes);

        // Crear nuevos registros de logs para registrar la cancelación de la orden
        Logs logs = new Logs(order, OrderStatus.CANCELLED);

        // Establecer la misma hora de inicio que los registros "beforeLogs" en los nuevos registros "logs"
        logs.setStartTime(beforeLogs.getStartTime());

        // Establecer la hora de finalización actual en los nuevos registros "logs"
        logs.setEndTime(LocalDateTime.now());

        // Obtener la fecha y hora de inicio de los nuevos registros "logs"
        LocalDateTime datetimeStartFinal = beforeLogs.getStartTime();

        // Obtener la fecha y hora de finalización de los nuevos registros "logs"
        LocalDateTime dateTimeEndFinal = logs.getEndTime();

        // Calcular la duración entre la fecha y hora de inicio y la fecha y hora de finalización en los nuevos registros "logs"
        Duration durationFinal = Duration.between(datetimeStartFinal, dateTimeEndFinal);

        // Convertir la duración a minutos en los nuevos registros "logs"
        Long minutesFinal = durationFinal.toMinutes();

        // Establecer el tiempo transcurrido en minutos en los nuevos registros "logs"
        logs.setTime(minutesFinal);

        // Guardar los nuevos registros de logs de cancelación en el repositorio
        logsRepositoy.save(logs);

        // Actualizar los registros "beforeLogs" con la hora de finalización y tiempo transcurrido actualizados
        logsRepositoy.save(beforeLogs);
    }
}