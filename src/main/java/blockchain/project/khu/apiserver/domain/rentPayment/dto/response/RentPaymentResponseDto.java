package blockchain.project.khu.apiserver.domain.rentPayment.dto.response;

import blockchain.project.khu.apiserver.domain.rentPayment.entity.RentPayment;
import blockchain.project.khu.apiserver.domain.rentPayment.enumerate.PaymentStatus;
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
    private Long tenantId;
    private Long propertyId;
    private BigDecimal amount;
    private LocalDate paidAt;
    private PaymentStatus status;

    public static RentPaymentResponseDto fromEntity(RentPayment payment) {
        return RentPaymentResponseDto.builder()
                .paymentId(payment.getId())
                .rentId(payment.getRent().getId())
                .tenantId(payment.getRent().getUser().getId())
                .propertyId(payment.getRent().getProperty().getId())
                .amount(payment.getAmount())
                .paidAt(payment.getPaidAt())
                .status(payment.getStatus())
                .build();
    }
}
