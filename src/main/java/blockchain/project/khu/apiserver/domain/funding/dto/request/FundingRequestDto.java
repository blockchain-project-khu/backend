package blockchain.project.khu.apiserver.domain.funding.dto.request;

import blockchain.project.khu.apiserver.domain.funding.entity.Funding;
import blockchain.project.khu.apiserver.domain.funding.entity.FundingStatus;
import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FundingRequestDto {

    @NotNull(message = "펀딩할 지분은 필수입니다.")
    private Integer percentage;

    public Funding toEntity(User user, Property property, FundingRequestDto dto){
        return Funding.builder()
                .user(user)
                .property(property)
                .percentage(dto.getPercentage())
                .status(FundingStatus.REQUESTED)
                .build();
    }
}
