package blockchain.project.khu.apiserver.domain.rentPayment.controller;

import blockchain.project.khu.apiserver.common.annotation.CurrentUser;
import blockchain.project.khu.apiserver.common.apiPayload.success.SuccessApiResponse;
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
    public SuccessApiResponse<RentPaymentResponseDto> payRent(@Valid @RequestBody RentPaymentRequestDto requestDto) {
        RentPaymentResponseDto rentPaymentResponseDto = rentPaymentService.payRent(requestDto);
        return SuccessApiResponse.payRent(rentPaymentResponseDto);
    }

    @GetMapping("/my")
    public SuccessApiResponse<List<RentPaymentResponseDto>> getMyPayments(
            @CurrentUser User user
    ) {
        System.out.println(user.getUsername());
        List<RentPaymentResponseDto> result = rentPaymentService.getMyPayments(user.getId());
        return SuccessApiResponse.getMyPayments(result);
    }
}
