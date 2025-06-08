package blockchain.project.khu.apiserver.domain.rentPayment.service;

import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.RentException;
import blockchain.project.khu.apiserver.domain.property.dto.response.PropertyPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.property.repository.PropertyRepository;
import blockchain.project.khu.apiserver.domain.rent.entity.Rent;
import blockchain.project.khu.apiserver.domain.rent.repository.RentRepository;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.request.RentPaymentRequestDto;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.response.RentPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.rentPayment.entity.RentPayment;
import blockchain.project.khu.apiserver.domain.rentPayment.repository.RentPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RentPaymentService {

    private final RentRepository rentRepository;
    private final RentPaymentRepository rentPaymentRepository;
    private final PropertyRepository propertyRepository;

    @Transactional
    public RentPaymentResponseDto payRent(RentPaymentRequestDto requestDto) {
        Rent rent = rentRepository.findById(requestDto.getRentId())
                .orElseThrow(RentException.RentNotFoundException::new);

        RentPayment payment = requestDto.toEntity(rent);
        RentPayment saved = rentPaymentRepository.save(payment);

        return RentPaymentResponseDto.fromEntity(saved);
    }

    public List<RentPaymentResponseDto> getMyPayments(Long userId) {
        List<Rent> rents = rentRepository.findByUserId(userId);

        return rents.stream()
                .flatMap(rent -> rentPaymentRepository.findByRentId(rent.getId()).stream())
                .map(RentPaymentResponseDto::fromEntity)
                .toList();
    }

    public List<PropertyPaymentResponseDto> getReceivedByProperty(Long ownerId) {
        return propertyRepository.findByUserId(ownerId)
                .stream()
                .map(property -> {
                    List<Long> rentIds = rentRepository
                            .findByPropertyId(property.getId())
                            .stream()
                            .map(Rent::getId)
                            .toList();

                    List<RentPayment> payments = rentIds.stream()
                            .flatMap(id -> rentPaymentRepository.findByRentId(id).stream())
                            .toList();

                    return PropertyPaymentResponseDto.fromEntity(property, payments);
                })
                .toList();
    }

    public List<RentPaymentResponseDto> getPaymentsByPropertyId(Long propertyId) {
        List<Rent> rents = rentRepository.findByPropertyId(propertyId);

        return rents.stream()
                .flatMap(rent -> rentPaymentRepository.findByRentId(rent.getId()).stream())
                .map(RentPaymentResponseDto::fromEntity)
                .toList();
    }

    public List<PropertyPaymentResponseDto> getUserPaymentsGroupedByProperty(Long userId) {
        // 1. 사용자가 세입자인 모든 Rent 가져오기
        List<Rent> userRents = rentRepository.findByUserId(userId);

        // 2. Property 별로 Rent 그룹화
        Map<Property, List<Rent>> rentsByProperty = userRents.stream()
                .collect(Collectors.groupingBy(Rent::getProperty));

        // 3. 각 Property에 대해 RentPayment를 모아서 DTO로 변환
        return rentsByProperty.entrySet().stream()
                .map(entry -> {
                    Property property = entry.getKey();
                    List<Rent> rents = entry.getValue();

                    // Rent ID 목록 추출
                    List<Long> rentIds = rents.stream()
                            .map(Rent::getId)
                            .toList();

                    // Rent ID에 해당하는 RentPayment를 모두 가져오기
                    List<RentPayment> userPayments = rentIds.stream()
                            .flatMap(rentId ->
                                    rentPaymentRepository.findByRentId(rentId).stream())
                            .toList();

                    // Property + RentPayment 목록으로 DTO 생성
                    return PropertyPaymentResponseDto.fromEntity(property, userPayments);
                })
                .toList();
    }


//    public List<PropertyPaymentResponseDto> getSendByProperty(Long userId) {
//        return rentRepository.findByUserId(userId) // 사용자가 임차인인 임대 정보 가져오기
//                .stream()
//                .collect(Collectors.groupingBy(Rent::getProperty)) // Property 별로 그룹화
//                .entrySet()
//                .stream()
//                .map(entry -> {
//                    Property property = entry.getKey();
//                    List<Rent> rents = entry.getValue();
//
//                    List<Long> rentIds = rents.stream().map(Rent::getId).toList();
//
//                    List<RentPayment> payments = rentIds.stream()
//                            .flatMap(id -> rentPaymentRepository.findByRentId(id).stream())
//                            .toList();
//
//                    return PropertyPaymentResponseDto.fromEntity(property, payments);
//                })
//                .toList();
//    }

}
