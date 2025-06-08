package blockchain.project.khu.apiserver.domain.rentPayment.controller;

import blockchain.project.khu.apiserver.common.annotation.CurrentUser;
import blockchain.project.khu.apiserver.common.apiPayload.success.SuccessApiResponse;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.response.RentPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.rentPayment.service.RentPaymentService;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rent-payment")
@RequiredArgsConstructor
public class RentPaymentController {

    private final RentPaymentService rentPaymentService;

    @PostMapping("/{propertyId}")
    @Operation(summary = "임대료 납부", description = "지정한 부동산 ID(propertyId)와 현재 로그인한 사용자에 대한 임대료를 납부합니다.")
    public SuccessApiResponse<RentPaymentResponseDto> payRent(
            @PathVariable Long propertyId,
            @Parameter(hidden = true) @CurrentUser User user
    ) {
        RentPaymentResponseDto rentPaymentResponseDto = rentPaymentService.payRent(propertyId, user.getId());
        return SuccessApiResponse.payRent(rentPaymentResponseDto);
    }

    @GetMapping("/my")
    @Operation(summary = "내 모든 임대료 납부 내역 조회", description = "로그인한 사용자의 전체 임대 계약에 대한 결제 내역을 조회합니다.")
    public SuccessApiResponse<List<RentPaymentResponseDto>> getMyPayments(
            @Parameter(hidden = true) @CurrentUser User user
    ) {
        List<RentPaymentResponseDto> result = rentPaymentService.getMyPayments(user.getId());
        return SuccessApiResponse.getMyPayments(result);
    }

    @GetMapping("/property/{propertyId}")
    @Operation(summary = "부동산별 결제 내역 조회", description = "특정 부동산(propertyId)에 대한 모든 결제 내역을 조회합니다.")
    public SuccessApiResponse<List<RentPaymentResponseDto>> getPaymentsByPropertyId(
            @PathVariable Long propertyId
    ) {
        List<RentPaymentResponseDto> result = rentPaymentService.getPaymentsByPropertyId(propertyId);
        return SuccessApiResponse.getPaymentsByPropertyId(result);
    }

    @GetMapping("/property/{propertyId}/my-payments")
    @Operation(summary = "특정 부동산에 대한 결제 내역 중 내 납부 내역만 조회", description = "로그인한 사용자의 특정 부동산에 대한 결제 내역을 조회합니다.")
    public SuccessApiResponse<List<RentPaymentResponseDto>> getMyPaymentsByPropertyId(
            @PathVariable Long propertyId,
            @Parameter(hidden = true) @CurrentUser User currentUser
    ) {
        List<RentPaymentResponseDto> result = rentPaymentService.getMyPaymentsByPropertyId(propertyId, currentUser.getId());
        return SuccessApiResponse.getMyPaymentsByPropertyId(result);
    }
}
