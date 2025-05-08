package blockchain.project.khu.apiserver.domain.funding.service;

import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.FundingException;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.PropertyException;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.UserException;
import blockchain.project.khu.apiserver.common.util.SecurityUtil;
import blockchain.project.khu.apiserver.domain.funding.dto.request.FundingRequestDto;
import blockchain.project.khu.apiserver.domain.funding.dto.response.FundingResponseDto;
import blockchain.project.khu.apiserver.domain.funding.entity.Funding;
import blockchain.project.khu.apiserver.domain.funding.entity.FundingStatus;
import blockchain.project.khu.apiserver.domain.funding.repository.FundingRepository;
import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.property.repository.PropertyRepository;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import blockchain.project.khu.apiserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FundingService {

    private final FundingRepository fundingRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    // 펀딩 생성
    public Long createFunding(Long propertyId, FundingRequestDto requestDto){
        Long userId = SecurityUtil.getCurrentMemberId();
        User user = userRepository.findById(userId).orElseThrow(UserException.UsernameNotExistException::new);
        Property property = propertyRepository.findById(propertyId).orElseThrow(PropertyException.PropertyNotFound::new);

        // 중복 펀딩 검사
        boolean exists = fundingRepository.existsByUserIdAndPropertyId(userId, propertyId);
        if (exists) {
            throw new FundingException.FundingAlreadyAppliedException();
        }

        /** 추후 : 매물 상태 검사 (예: 펀딩 마감 상태) **/
//        if (property.isFundingClosed()) { // 예시 메서드
//            throw new FundingException.FundingClosedException();
//        }

        Funding funding = requestDto.toEntity(user, property, requestDto);
        return fundingRepository.save(funding).getId();
    }

    // 펀딩 단건 조회
    public FundingResponseDto getFunding(Long fundingId){
        Funding funding = fundingRepository.findById(fundingId).orElseThrow(FundingException.FundingNotFoundException::new);
        return FundingResponseDto.fromEntity(funding);
    }

    // 펀딩 목록 조회 - 구매자
    public List<FundingResponseDto> getFundingList(FundingStatus status){
        Long userId = SecurityUtil.getCurrentMemberId();
        return fundingRepository.findMyFundingsByStatus(userId, status);
    }

    // 펀딩 목록 조회 - 매물별
    public List<FundingResponseDto> getFundingListByProperty(Long propertyId, FundingStatus status){
        return fundingRepository.findAllByPropertyId(propertyId,status);
    }

    // 펀딩 수정 - 구매자
    public FundingResponseDto updateFunding(Long fundingId, FundingRequestDto requestDto){
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(FundingException.FundingNotFoundException::new);

        Long userId = SecurityUtil.getCurrentMemberId();
        if (!funding.getUser().getId().equals(userId)) {
            throw new FundingException.UnauthorizedAccessException();
        }

        // 완료된 펀딩은 수정 불가
        if (funding.getStatus() == FundingStatus.COMPLETED) {
            throw new FundingException.FundingAlreadySuccessException();
        }

        funding.updateFunding(requestDto);
        return FundingResponseDto.fromEntity(funding);
    }

    // 펀딩 상태 수정 - 판매자
    public FundingResponseDto updateFundingStatus(Long fundingId, FundingStatus status){
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(FundingException.FundingNotFoundException::new);

        Long userId = SecurityUtil.getCurrentMemberId();
        if (!funding.getProperty().getUser().getId().equals(userId)) {
            throw new FundingException.UnauthorizedAccessException();
        }

        // 이미 완료된 펀딩에 대해 상태 변경 불가
        if (funding.getStatus() == FundingStatus.COMPLETED) {
            throw new FundingException.FundingAlreadySuccessException();
        }

        funding.updateFundingStatus(status);
        return FundingResponseDto.fromEntity(funding);
    }

    // 펀딩 삭제
    public void deleteFunding(Long fundingId){
        Funding funding = fundingRepository.findById(fundingId).orElseThrow(FundingException.FundingNotFoundException::new);
        Long userId = SecurityUtil.getCurrentMemberId();
        if (!funding.getUser().getId().equals(userId)) {
            throw new FundingException.UnauthorizedAccessException(); // 예외 따로 정의
        }
        fundingRepository.delete(funding);
    }
}
