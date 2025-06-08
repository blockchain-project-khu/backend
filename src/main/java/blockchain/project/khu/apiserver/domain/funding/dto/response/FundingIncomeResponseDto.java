package blockchain.project.khu.apiserver.domain.funding.dto.response;

import blockchain.project.khu.apiserver.domain.rentPayment.dto.response.RentPaymentResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class FundingIncomeResponseDto {
    private Long propertyId;
    private Integer fundingPercentage;
    private BigDecimal totalIncome;
    private List<RentPaymentResponseDto> payments;

    public static FundingIncomeResponseDto of(
            Long propertyId,
            Integer percentage,
            BigDecimal income,
            List<RentPaymentResponseDto> payments
    ) {
        return FundingIncomeResponseDto.builder()
                .propertyId(propertyId)
                .fundingPercentage(percentage)
                .totalIncome(income)
                .payments(payments)
                .build();
    }
}
