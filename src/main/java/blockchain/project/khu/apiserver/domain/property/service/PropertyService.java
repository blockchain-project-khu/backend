package blockchain.project.khu.apiserver.domain.property.service;

import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.PropertyException;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.UserException;
import blockchain.project.khu.apiserver.common.util.SecurityUtil;
import blockchain.project.khu.apiserver.domain.property.dto.request.PropertyRequestDto;
import blockchain.project.khu.apiserver.domain.property.dto.response.PropertyResponseDto;
import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.property.repository.PropertyRepository;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import blockchain.project.khu.apiserver.domain.user.jwt.JWTUtil;
import blockchain.project.khu.apiserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Security;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    // 등록
    public PropertyResponseDto createProperty(PropertyRequestDto request){
        Long userId = SecurityUtil.getCurrentMemberId();
        User user = userRepository.findById(userId).orElseThrow(UserException.UsernameNotExistException::new);

        Property property = request.toEntity(user);

        propertyRepository.save(property);

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
}
