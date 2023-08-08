package com.example.restaurantepragma.maps;

import com.example.restaurantepragma.dto.claim.ResponseClaimAdminDTO;
import com.example.restaurantepragma.dto.claim.ResponseClaimDTO;
import com.example.restaurantepragma.entities.Claim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ClaimMapper {
    @Mappings({
            @Mapping(source = "orderId", target = "orden"),
            @Mapping(source = "reason", target = "razon"),
            @Mapping(source = "claimStatus", target = "estado")
    })
    ResponseClaimDTO toClaimDTO(Claim claim);
    List<ResponseClaimDTO> toClaimsDTO(List<Claim> claims);
    @Mappings({
            @Mapping(source = "claimStatus",target = "estado"),
            @Mapping(source = "response",target = "respuesta")
    })
    ResponseClaimAdminDTO toResponseDTO(Claim claim);

    List<ResponseClaimAdminDTO> toResponsesDTO(List<Claim> claims);
}