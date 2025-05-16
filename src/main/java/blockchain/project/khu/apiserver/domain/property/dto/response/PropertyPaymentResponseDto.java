package blockchain.project.khu.apiserver.domain.property.dto.response;

import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.response.RentPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.rentPayment.entity.RentPayment;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyPaymentResponseDto {
    private Long propertyId;
    private String propertyName;
    private BigDecimal totalReceived;
    private int paymentCount;
    private List<RentPaymentResponseDto> payments;

    public static PropertyPaymentResponseDto fromEntity(
            Property property,
            List<RentPayment> paymentEntities
    ) {
        List<RentPaymentResponseDto> paymentDtos = paymentEntities.stream()
                .map(RentPaymentResponseDto::fromEntity)
                .toList();

        BigDecimal total = paymentDtos.stream()
                .map(RentPaymentResponseDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return PropertyPaymentResponseDto.builder()
                .propertyId(property.getId())
                .propertyName(property.getName())
                .totalReceived(total)
                .paymentCount(paymentDtos.size())
                .payments(paymentDtos)
                .build();
    }
}