package blockchain.project.khu.apiserver.domain.funding.service;

import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.FundingException;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.PropertyException;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.UserException;
import blockchain.project.khu.apiserver.common.util.SecurityUtil;
import blockchain.project.khu.apiserver.domain.funding.dto.request.FundingRequestDto;
import blockchain.project.khu.apiserver.domain.funding.dto.response.FundingIncomeResponseDto;
import blockchain.project.khu.apiserver.domain.funding.dto.response.FundingResponseDto;
import blockchain.project.khu.apiserver.domain.funding.entity.Funding;
import blockchain.project.khu.apiserver.domain.funding.entity.FundingStatus;
import blockchain.project.khu.apiserver.domain.funding.repository.FundingRepository;
import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.property.entity.PropertyStatus;
import blockchain.project.khu.apiserver.domain.property.repository.PropertyRepository;
import blockchain.project.khu.apiserver.domain.property.service.PropertyService;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.response.RentPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.rentPayment.entity.RentPayment;
import blockchain.project.khu.apiserver.domain.rentPayment.repository.RentPaymentRepository;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import blockchain.project.khu.apiserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FundingService {

    private final FundingRepository fundingRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final RentPaymentRepository rentPaymentRepository;
    private final PropertyService propertyService;

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

        // 기존 합산 퍼센트 조회
        int existingSum = property.getFundings().stream()
                .mapToInt(Funding::getPercentage)
                .sum();

        // 100% 초과 여부 검사
        if (existingSum + requestDto.getPercentage() > 100) {
            throw new RuntimeException(
                    "이미 진행된 퍼센트(" + existingSum + "%) + 요청 퍼센트("
                            + requestDto.getPercentage() + "%)가 100%를 초과합니다.");
        }

        Funding funding = requestDto.toEntity(user, property, requestDto);
        property.addFundingEntity(funding);

        if(property.getCurrentFundingPercent() == 100) {
            // 매물 상태 변경
            property.updatePropertyStatus();

            // 펀딩 상태 변경
            for(Funding fundingByProperty: fundingRepository.findAllByProperty(property)){
                fundingByProperty.updateFundingStatus(FundingStatus.COMPLETED);
            }
            funding.updateFundingStatus(FundingStatus.COMPLETED);
        }

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


    public List<FundingIncomeResponseDto> getMyRentalIncome(Long userId) {
        List<Funding> fundings = fundingRepository.findByUserIdAndProperty_Status(userId, PropertyStatus.SOLD);

        return fundings.stream()
                .map(funding -> {
                    Long propertyId = funding.getProperty().getId();
                    Integer percentage = funding.getPercentage();  // 예: 20

                    List<RentPayment> payments = rentPaymentRepository.findByRent_Property_Id(propertyId);

                    // 각 payment에 대해 펀딩 비율 적용
                    List<RentPaymentResponseDto> adjustedPayments = payments.stream()
                            .map(p -> RentPaymentResponseDto.fromEntityWithAdjustedAmount(p, percentage))
                            .toList();

                    // 조정된 amount만 합산
                    BigDecimal income = adjustedPayments.stream()
                            .map(RentPaymentResponseDto::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return FundingIncomeResponseDto.of(propertyId, percentage, income, adjustedPayments);
                })
                .toList();
    }

}
