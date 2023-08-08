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
        Logs logs = new Logs(order,OrderStatus.EARRING);
        logs.setStartTime(LocalDateTime.now());
        logsRepositoy.save(logs);
    }
    public void inPreparationSave(Order order){
        Logs logs = new Logs(order,OrderStatus.IN_PREPARATION);
        logs.setStartTime(LocalDateTime.now());
        logsRepositoy.save(logs);
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);
        beforeLogs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);
        Long minutes = duration.toMinutes();
        beforeLogs.setTime(minutes);
        logsRepositoy.save(beforeLogs);
    }
    public void readySave(Order order){
        Logs logs = new Logs(order,OrderStatus.READY);
        logs.setStartTime(LocalDateTime.now());
        logsRepositoy.save(logs);
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.IN_PREPARATION);
        beforeLogs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);
        Long minutes = duration.toMinutes();
        beforeLogs.setTime(minutes);
        logsRepositoy.save(beforeLogs);
    }
    public void deliveredSave(Order order){
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.READY);
        beforeLogs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);
        Long minutes = duration.toMinutes();
        beforeLogs.setTime(minutes);
        Logs startLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);
        Logs logs = new Logs(order,OrderStatus.DELIVERED);
        logs.setStartTime(startLogs.getStartTime());
        logs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStartFinal = startLogs.getStartTime();
        LocalDateTime dateTimeEndFinal = logs.getEndTime();
        Duration durationFinal = Duration.between(datetimeStartFinal, dateTimeEndFinal);
        Long minutesFinal = durationFinal.toMinutes();
        logs.setTime(minutesFinal);
        logsRepositoy.save(logs);
        logsRepositoy.save(beforeLogs);
    }
    public void cancelSave(Order order){
        Logs beforeLogs = logsRepositoy.findByOrderLogIdAndStatus(order, OrderStatus.EARRING);
        beforeLogs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStart = beforeLogs.getStartTime();
        LocalDateTime dateTimeEnd = beforeLogs.getEndTime();
        Duration duration = Duration.between(datetimeStart, dateTimeEnd);
        Long minutes = duration.toMinutes();
        beforeLogs.setTime(minutes);
        Logs logs = new Logs(order,OrderStatus.CANCELLED);
        logs.setStartTime(beforeLogs.getStartTime());
        logs.setEndTime(LocalDateTime.now());
        LocalDateTime datetimeStartFinal = beforeLogs.getStartTime();
        LocalDateTime dateTimeEndFinal = logs.getEndTime();
        Duration durationFinal = Duration.between(datetimeStartFinal, dateTimeEndFinal);
        Long minutesFinal = durationFinal.toMinutes();
        logs.setTime(minutesFinal);
        logsRepositoy.save(logs);
        logsRepositoy.save(beforeLogs);
    }

}
