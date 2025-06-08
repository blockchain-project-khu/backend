package blockchain.project.khu.apiserver.domain.rentPayment.controller;

import blockchain.project.khu.apiserver.common.annotation.CurrentUser;
import blockchain.project.khu.apiserver.common.apiPayload.success.SuccessApiResponse;
import blockchain.project.khu.apiserver.domain.property.dto.response.PropertyPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.request.RentPaymentRequestDto;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.response.RentPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.rentPayment.service.RentPaymentService;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rent-payment")
@RequiredArgsConstructor
public class RentPaymentController {

    private final RentPaymentService rentPaymentService;

    @PostMapping
    public SuccessApiResponse<RentPaymentResponseDto> payRent(
            @Valid @RequestBody RentPaymentRequestDto requestDto,
            @CurrentUser User user
    ) {
        RentPaymentResponseDto rentPaymentResponseDto = rentPaymentService.payRent(requestDto, user.getId());
        return SuccessApiResponse.payRent(rentPaymentResponseDto);
    }

    @GetMapping("/my")
    public SuccessApiResponse<List<RentPaymentResponseDto>> getMyPayments(
            @CurrentUser User user
    ) {
        List<RentPaymentResponseDto> result = rentPaymentService.getMyPayments(user.getId());
        return SuccessApiResponse.getMyPayments(result);
    }

    @GetMapping("/by-property")
    public SuccessApiResponse<List<PropertyPaymentResponseDto>> getPaymentsByProperty(
            @CurrentUser User user
    ) {
        List<PropertyPaymentResponseDto> result = rentPaymentService.getReceivedByProperty(user.getId());
        return SuccessApiResponse.getPaymentsByProperty(result);
    }

    @GetMapping("/property/{propertyId}")
    public SuccessApiResponse<List<RentPaymentResponseDto>> getPaymentsByPropertyId(
            @PathVariable Long propertyId
    ) {
        List<RentPaymentResponseDto> result = rentPaymentService.getPaymentsByPropertyId(propertyId);
        return SuccessApiResponse.getPaymentsByPropertyId(result);
    }

    @GetMapping("/property/{propertyId}/my-payments")
    public SuccessApiResponse<List<RentPaymentResponseDto>> getMyPaymentsByPropertyId(
            @PathVariable Long propertyId,
            @CurrentUser User currentUser
    ) {
        List<RentPaymentResponseDto> result = rentPaymentService.getMyPaymentsByPropertyId(propertyId, currentUser.getId());
        return SuccessApiResponse.getMyPaymentsByPropertyId(result);
    }
}
