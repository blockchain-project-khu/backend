package blockchain.project.khu.apiserver.domain.rentPayment.dto.request;

import blockchain.project.khu.apiserver.domain.rent.entity.Rent;
import blockchain.project.khu.apiserver.domain.rentPayment.entity.RentPayment;
import blockchain.project.khu.apiserver.domain.rentPayment.enumerate.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class RentPaymentRequestDto {

    @NotNull(message = "rentId는 필수입니다.")
    private Long rentId;

    @NotNull(message = "amount는 필수입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "금액은 0보다 커야 합니다.")
    private BigDecimal amount;

    public RentPayment toEntity(Rent rent) {
        return RentPayment.builder()
                .rent(rent)
                .amount(amount)
                .paidAt(LocalDate.now())
                .status(PaymentStatus.PAID)
                .build();
    }
}
