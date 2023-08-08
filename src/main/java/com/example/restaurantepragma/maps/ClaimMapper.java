package com.example.restaurantepragma.maps;

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
}