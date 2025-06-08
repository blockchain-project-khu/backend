package blockchain.project.khu.apiserver.domain.rent.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class RentRequestDto {

    @Schema(description = "임대할 매물 ID", example = "19")
    private Long propertyId;

    @Schema(description = "임대 시작일 (YYYY-MM-DD)", example = "2025-05-03")
    private LocalDate startDate;

    @Schema(description = "임대 종료일 (YYYY-MM-DD)", example = "2025-12-25")
    private LocalDate endDate;

    @Schema(description = "월세 금액", example = "700000")
    private BigDecimal monthlyRent;

    @Schema(description = "보증금", example = "6000000")
    private BigDecimal deposit;

    @Schema(description = "매달 납부일 (1~31)", example = "4")
    private Integer paymentDay;
}