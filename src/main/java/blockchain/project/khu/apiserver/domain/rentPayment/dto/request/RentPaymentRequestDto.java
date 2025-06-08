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

    @NotNull(message = "propertyId는 필수입니다.")
    private Long propertyId;

    public RentPayment toEntity(Rent rent) {
        return RentPayment.builder()
                .rent(rent)
                .amount(rent.getMonthlyRent())
                .paidAt(LocalDate.now())
                .status(PaymentStatus.PAID)
                .build();
    }
}
