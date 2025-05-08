package blockchain.project.khu.apiserver.domain.funding.repository;

import blockchain.project.khu.apiserver.domain.funding.dto.response.FundingResponseDto;
import blockchain.project.khu.apiserver.domain.funding.entity.FundingStatus;

import java.util.List;

public interface FundingRepositoryCustom {
    /**
     * 매물에 해당하는 펀딩 목록을 조회합니다.
     * status가 null이면 전체, 아니면 해당 상태만 리턴합니다.
     */
    public List<FundingResponseDto> findMyFundingsByStatus(Long userId, FundingStatus status);
    public List<FundingResponseDto> findAllByPropertyId(Long propertyId, FundingStatus status);

}
