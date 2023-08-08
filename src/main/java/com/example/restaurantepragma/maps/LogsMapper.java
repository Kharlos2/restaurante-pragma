package com.example.restaurantepragma.maps;

import com.example.restaurantepragma.dto.logs.ResponseLogsDTO;
import com.example.restaurantepragma.entities.Logs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LogsMapper {

    @Mappings({
            @Mapping(source = "status", target = "estadoDeLaOrden"),
            @Mapping(source = "startTime", target = "tiempoInicio"),
            @Mapping(source = "endTime", target = "tiempoFin"),
            @Mapping(source = "time", target = "minutosDuracion"),
    })
    ResponseLogsDTO toLogsDTO(Logs logs);

    List<ResponseLogsDTO> toLogsListDTO(List<Logs> logsList);

}
