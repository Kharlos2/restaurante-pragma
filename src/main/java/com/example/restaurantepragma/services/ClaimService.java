package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.claim.ClaimResquestDTO;
import com.example.restaurantepragma.dto.claim.ResponseClaimAdminDTO;
import com.example.restaurantepragma.dto.claim.ResponseClaimDTO;
import com.example.restaurantepragma.dto.claim.ResponseRequestClaimDTO;
import com.example.restaurantepragma.entities.Claim;
import com.example.restaurantepragma.entities.Customer;
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
import com.example.restaurantepragma.utils.NotificationMananger;
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

            // Buscar la orden correspondiente al código de orden proporcionado en el DTO de solicitud de reclamo.
            Optional<Order> orderOptional = orderRepository.findByOrderCode(claimResquestDTO.getCodigoOrden());
            if (orderOptional.isEmpty())
                throw new Exception(OrderResponses.NOT_FOUNT_ORDER.getMessage()); // Lanzar excepción si no se encuentra la orden.
            else if (claimResquestDTO.getRazon().isEmpty())
                throw new Exception(ClaimResponses.EMPTY_REASON.getMessage()); // Lanzar excepción si la razón del reclamo está vacía.
            else if (claimResquestDTO.getCodigoOrden().isEmpty())
                throw new Exception(ClaimResponses.EMPTY_CODE.getMessage()); // Lanzar excepción si el código de orden en el DTO de reclamo está vacío.

            // Crear una instancia de la clase Claim para representar el reclamo.
            Claim claim = new Claim();

            // Obtener la orden desde el Optional<Order>.
            Order order = orderOptional.get();

            // Establecer la orden asociada al reclamo.
            claim.setOrderId(order);

            // Establecer la razón del reclamo desde el DTO de solicitud de reclamo.
            claim.setReason(claimResquestDTO.getRazon());

            // Establecer el estado del reclamo como "GENERATED".
            claim.setClaimStatus(ClaimStatus.GENERATED);

            // Guardar el reclamo en la base de datos y obtener una versión actualizada.
            ResponseClaimDTO responseClaimDTO = claimMapper.toClaimDTO(claimRepository.save(claim));

            // Crear una lista de reclamos y agregar el reclamo recién creado.
            List<Claim> claims = new ArrayList<>();
            claims.add(claim);

            // Asignar la lista de reclamos a la orden.
            order.setClaimId(claims);

            // Devolver el DTO de respuesta del reclamo.
            return responseClaimDTO;

        } catch (Exception e) {
            // Capturar cualquier excepción ocurrida y lanzar una excepción con el mensaje original.
            throw new Exception(e.getMessage());
        }
    }

    public List<ResponseClaimDTO> findAllEarring()throws Exception{
        try {
            // Aquí se intenta ejecutar una operación para buscar reclamos en el repositorio.
            // La función findByClaimStatus busca reclamos según su estado, en este caso, ClaimStatus.GENERATED.
            // Esto devuelve una lista de reclamos que tienen el estado "GENERATED".
            List<Claim> claimList = claimRepository.findByClaimStatus(ClaimStatus.GENERATED);

            // Luego, se utiliza el objeto claimMapper para convertir la lista de objetos Claim a objetos ResponseClaimDTO.
            // La función toClaimsDTO realiza esta conversión utilizando la lógica definida en claimMapper.
            List<ResponseClaimDTO> responseClaimDTOList = claimMapper.toClaimsDTO(claimList);

            // Finalmente, se devuelve la lista de objetos ResponseClaimDTO que representan los reclamos encontrados.
            return responseClaimDTOList;

        } catch (Exception e) {
            // Si ocurre algún error durante el proceso, se captura la excepción.
            // Luego, se lanza una nueva excepción, envolviendo el mensaje de la excepción original.
            throw new Exception(e.getMessage());
        }
    }

    public ResponseClaimAdminDTO responseClaim(ResponseRequestClaimDTO requestClaim, Long idEmpleado, String password, ClaimStatus respuesta)throws Exception{
        try {
            // Buscar al empleado en la base de datos por su ID
            Optional<Employee> employeeOptional = employeeRepository.findById(idEmpleado);

            // Verificar si el empleado existe en la base de datos
            if (employeeOptional.isEmpty()) {
                throw new Exception(EmployeeResponses.NOT_FOUNT_EMPLOYEE.getMessage());
            } else if (!employeeOptional.get().getPassword().equals(password)) {
                throw new Exception(EmployeeResponses.INCORRECT_PASSWORD.getMessage());
            }

            // Buscar la solicitud de reclamo en la base de datos por su ID
            Optional<Claim> claimOptional = claimRepository.findById(requestClaim.getId());

            // Verificar si la solicitud de reclamo existe en la base de datos
            if (claimOptional.isEmpty()) {
                throw new Exception(ClaimResponses.NOT_FOUNT_CLAIM.getMessage());
            }

            // Obtener la solicitud de reclamo desde el Optional
            Claim claim = claimOptional.get();

            // Verificar si la respuesta es válida (ACEPTADA o RECHAZADA)
            if (respuesta != ClaimStatus.ACCEPTED && respuesta != ClaimStatus.REJECTED) {
                throw new Exception(ClaimResponses.NO_STATUS.getMessage());
            } else if (requestClaim.getJustificacion().isEmpty()) {
                throw new Exception(ClaimResponses.UNANSWERED.getMessage());
            } else if (claim.getClaimStatus() != ClaimStatus.GENERATED) {
                throw new Exception(ClaimResponses.ANSWERED.getMessage());
            }

            // Establecer la justificación en la solicitud de reclamo
            claim.setResponse(requestClaim.getJustificacion());

            // Establecer el estado de la solicitud de reclamo como la respuesta proporcionada
            claim.setClaimStatus(respuesta);

            // Enviar notificación relacionada con el estado del reclamo y la justificación
            NotificationMananger.sendNotificationToClaims(respuesta, requestClaim.getJustificacion());

            // Guardar los cambios en la base de datos y devolver la solicitud de reclamo mapeada a un DTO de respuesta
            return claimMapper.toResponseDTO(claimRepository.save(claim));
        } catch (Exception e) {
            // Capturar cualquier excepción ocurrida durante el proceso y relanzarla con un mensaje
            throw new Exception(e.getMessage());
        }
    }
    public ResponseClaimAdminDTO findById(Long id)throws Exception{
        try {
            // Intentamos buscar una reclamación por su ID utilizando el repositorio de reclamaciones.
            Optional<Claim> claimOptional = claimRepository.findById(id);

            // Verificamos si la reclamación existe en base al resultado de la búsqueda.
            if (claimOptional.isEmpty()) {
                // Si no se encuentra la reclamación, lanzamos una excepción con un mensaje personalizado.
                throw new Exception(ClaimResponses.NOT_FOUNT_CLAIM.getMessage());
            }

            // Si encontramos la reclamación, convertimos el objeto Claim a su representación DTO de respuesta
            // utilizando el mapeador (mapper) y luego lo devolvemos.
            return claimMapper.toResponseDTO(claimOptional.get());

        } catch (Exception e) {
            // Si ocurre cualquier excepción durante el proceso, la capturamos y relanzamos con el mismo mensaje.
            throw new Exception(e.getMessage());
        }
    }
}
