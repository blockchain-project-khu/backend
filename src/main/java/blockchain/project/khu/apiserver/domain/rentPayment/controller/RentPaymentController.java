package blockchain.project.khu.apiserver.domain.rentPayment.controller;

import blockchain.project.khu.apiserver.common.apiPayload.success.SuccessApiResponse;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.request.RentPaymentRequestDto;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.response.RentPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.rentPayment.service.RentPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
