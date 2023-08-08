package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.claim.ClaimResquestDTO;
import com.example.restaurantepragma.dto.claim.ResponseClaimDTO;
import com.example.restaurantepragma.dto.claim.ResponseRequestClaimDTO;
import com.example.restaurantepragma.entities.Claim;
import com.example.restaurantepragma.entities.Employee;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.enums.ClaimResponses;
import com.example.restaurantepragma.enums.ClaimStatus;
import com.example.restaurantepragma.enums.EmployeeResponses;
import com.example.restaurantepragma.enums.OrderResponses;
import com.example.restaurantepragma.maps.ClaimMapper;
import com.example.restaurantepragma.maps.OrderMapper;
import com.example.restaurantepragma.repository.ClaimRepository;
import com.example.restaurantepragma.repository.EmployeeRepository;
import com.example.restaurantepragma.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private ClaimMapper claimMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private EmployeeRepository employeeRepository;

    public ResponseClaimDTO save(ClaimResquestDTO claimResquestDTO)throws Exception{
        try {
            Optional<Order> orderOptional = orderRepository.findByOrderCode(claimResquestDTO.getCodigoOrden());
            if (orderOptional.isEmpty())throw new Exception(OrderResponses.NOT_FOUNT_ORDER.getMessage());
            Claim claim = new Claim();
            Order order = orderOptional.get();
            claim.setOrderId(order);
            claim.setReason(claimResquestDTO.getRazon());
            claim.setClaimStatus(ClaimStatus.GENERATED);
            ResponseClaimDTO responseClaimDTO = claimMapper.toClaimDTO(claimRepository.save(claim));
            List<Claim> claims = new ArrayList<>();
            claims.add(claim);
            order.setClaimId(claims);
            return responseClaimDTO;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<ResponseClaimDTO> findAllEarring()throws Exception{
        try {
            return claimMapper.toClaimsDTO(claimRepository.findByClaimStatus(ClaimStatus.GENERATED));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public ResponseClaimDTO responseClaim(ResponseRequestClaimDTO requestClaim, Long idEmpleado, String password)throws Exception{
        try {
            Optional<Employee> employeeOptional = employeeRepository.findById(idEmpleado);
            if (employeeOptional.isEmpty())throw new Exception(EmployeeResponses.NOT_FOUNT_EMPLOYEE.getMessage());
            else if (!employeeOptional.get().getPassword().equals(password)) throw new Exception(EmployeeResponses.INCORRECT_PASSWORD.getMessage());

            Optional<Claim> claimOptional = claimRepository.findById(requestClaim.getId());
            if (claimOptional.isEmpty()) throw new Exception(ClaimResponses.NOT_FOUNT_CLAIM.getMessage());
            Claim claim = claimOptional.get();
            if (!claim.getClaimStatus().equals(ClaimStatus.ACCEPTED) && !claim.getClaimStatus().equals(ClaimStatus.REJECTED)){
                throw new Exception(ClaimResponses.NO_STATUS.getMessage());
            }else if (claim.getResponse().isEmpty()) throw new Exception(ClaimResponses.UNANSWERED.getMessage());
            claim.setResponse(requestClaim.getJustificacion());
            claim.setClaimStatus(requestClaim.getRespuesta());
            return claimMapper.toClaimDTO(claimRepository.save(claim));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}