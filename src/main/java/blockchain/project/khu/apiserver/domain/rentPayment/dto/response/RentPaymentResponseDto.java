package blockchain.project.khu.apiserver.domain.rentPayment.dto.response;

import blockchain.project.khu.apiserver.domain.rentPayment.entity.RentPayment;
import blockchain.project.khu.apiserver.domain.rentPayment.enumerate.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentPaymentResponseDto {

    private Long paymentId;
    private Long rentId;
    private Long userId;
    private Long propertyId;
    private BigDecimal amount;
    private @JsonFormat(pattern = "yyyy-MM-dd") LocalDate paidAt;
    private PaymentStatus status;

    public static RentPaymentResponseDto fromEntity(RentPayment payment) {
        return RentPaymentResponseDto.builder()
                .paymentId(payment.getId())
                .rentId(payment.getRent().getId())
                .userId(payment.getRent().getUser().getId())
                .propertyId(payment.getRent().getProperty().getId())
                .amount(payment.getAmount())
                .paidAt(payment.getPaidAt())
                .status(payment.getStatus())
                .build();
    }

    // 펀딩 비율을 반영한 amount 계산
    public static RentPaymentResponseDto fromEntityWithAdjustedAmount(RentPayment payment, Integer percentage) {
        BigDecimal adjusted = payment.getAmount()
                .multiply(BigDecimal.valueOf(percentage))
                .divide(BigDecimal.valueOf(100));

        return RentPaymentResponseDto.builder()
                .paymentId(payment.getId())
                .rentId(payment.getRent().getId())
                .userId(payment.getRent().getUser().getId())
                .propertyId(payment.getRent().getProperty().getId())
                .amount(adjusted)
                .paidAt(payment.getPaidAt())
                .status(payment.getStatus())
                .build();
    }
}
