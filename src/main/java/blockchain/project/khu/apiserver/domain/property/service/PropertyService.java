package blockchain.project.khu.apiserver.domain.property.service;

import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.PropertyException;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.UserException;
import blockchain.project.khu.apiserver.common.util.SecurityUtil;
import blockchain.project.khu.apiserver.domain.property.dto.request.PropertyRequestDto;
import blockchain.project.khu.apiserver.domain.property.dto.response.PropertyResponseDto;
import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.property.repository.PropertyRepository;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import blockchain.project.khu.apiserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PropertyContractService propertyContractService;
    // 등록
    public PropertyResponseDto createProperty(PropertyRequestDto request){
        Long userId = SecurityUtil.getCurrentMemberId();
        User user = userRepository.findById(userId).orElseThrow(UserException.UsernameNotExistException::new);

        // 1. 블록체인 등록 전 propertyCount 조회
        BigInteger beforeCount;
        try {
            beforeCount = propertyContractService.getPropertyCount();
        } catch (Exception ex) {
            throw new RuntimeException("블록체인에서 propertyCount 조회 실패", ex);
        }

        BigInteger newBlockchainIdBI = beforeCount.add(BigInteger.ONE);
        Long newBlockchainId = newBlockchainIdBI.longValue();

        Property property = request.toEntity(user, newBlockchainId);
        Property savedProperty = propertyRepository.save(property);


        // 2. 블록체인에 registerProperty 트랜잭션 전송
        try {
            propertyContractService.registerProperty(
                    savedProperty.getId(),
                    new BigInteger("1000000000000000000"), // 1 KAIA
                    BigInteger.valueOf(3600 * 24 * 7),     // 일주일(초)
                    new BigInteger("10000000000000000")    // 0.01 KAIA
            );
        } catch (Exception ex) {
            throw new RuntimeException("블록체인에 registerProperty 전송 실패", ex);
        }

        // 3. 블록체인 등록 후 propertyCount 조회 및 정상 여부 확인
        BigInteger afterCount;
        try {
            afterCount = propertyContractService.getPropertyCount();
        } catch (Exception ex) {
            throw new RuntimeException("블록체인에서 등록 후 propertyCount 조회 실패", ex);
        }

        if (!afterCount.equals(beforeCount.add(BigInteger.ONE))) {
            throw new RuntimeException(
                    "registerProperty 후 propertyCount가 기대값과 다릅니다. before=" +
                            beforeCount + ", after=" + afterCount
            );
        }

        // 4. (선택) 등록된 매물 ID로 sharePrice 조회
        BigInteger newPropertyId = afterCount;
        BigInteger sharePriceWei;
        try {
            sharePriceWei = propertyContractService.getSharePrice(newPropertyId);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "블록체인에서 getSharePrice 조회 실패 (propertyId=" + newPropertyId + ")",
                    ex
            );
        }
        return PropertyResponseDto.fromEntity(property);
    }

    // 단건 조회
    public PropertyResponseDto getProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(PropertyException.PropertyNotFound::new);
        return PropertyResponseDto.fromEntity(property);
    }

    // 목록 조회
    public List<PropertyResponseDto> getProperties() {
        List<Property> propertyList = propertyRepository.findAll();
        return propertyList.stream()
                .map(PropertyResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 삭제
    public void deleteProperty(Long propertyId){
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(PropertyException.PropertyNotFound::new);
        propertyRepository.delete(property);

    }

    // 수정
    public PropertyResponseDto updateProperty(Long propertyId, PropertyRequestDto requestDto){
        Property property = propertyRepository.findById(propertyId).orElseThrow(PropertyException.PropertyNotFound::new);
        property.update(requestDto);
        return PropertyResponseDto.fromEntity(property);
    }

    // [추가] 자기가 등록한 매물 리스트 조회
    public List<PropertyResponseDto> getSales() {
        Long userId = SecurityUtil.getCurrentMemberId();

        List<Property> sales = propertyRepository.findByUserId(userId);

        return sales.stream()
                .map(PropertyResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

}
