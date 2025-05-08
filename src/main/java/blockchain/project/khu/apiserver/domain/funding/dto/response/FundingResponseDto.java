package blockchain.project.khu.apiserver.domain.funding.dto.response;

import blockchain.project.khu.apiserver.domain.funding.entity.Funding;
import blockchain.project.khu.apiserver.domain.funding.entity.FundingStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundingResponseDto {

    private Long fundingId;
    private Long userId;
    private Long propertyId;
    private Integer amount;
    private FundingStatus status;

    @QueryProjection
    public FundingResponseDto(Long fundingId, Integer amount, FundingStatus status, Long userId, Long propertyId) {
        this.fundingId = fundingId;
        this.amount = amount;
        this.status = status;
        this.userId = userId;
        this.propertyId = propertyId;
    }

    public static FundingResponseDto fromEntity(Funding funding) {
        return new FundingResponseDto(
                funding.getId(),
                funding.getAmount(),
                funding.getStatus(),
                funding.getUser().getId(),
                funding.getProperty().getId()
        );
    }
}
