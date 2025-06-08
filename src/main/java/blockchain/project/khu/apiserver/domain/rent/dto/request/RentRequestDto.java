package blockchain.project.khu.apiserver.domain.rent.dto.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class RentRequestDto {
    private Long propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal monthlyRent;
    private BigDecimal deposit;
    private Integer paymentDay;
}