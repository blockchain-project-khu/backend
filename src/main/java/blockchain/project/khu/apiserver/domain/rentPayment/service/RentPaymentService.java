package blockchain.project.khu.apiserver.domain.rentPayment.service;

import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.RentException;
import blockchain.project.khu.apiserver.domain.property.dto.response.PropertyPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.property.repository.PropertyRepository;
import blockchain.project.khu.apiserver.domain.rent.entity.Rent;
import blockchain.project.khu.apiserver.domain.rent.repository.RentRepository;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.request.RentPaymentRequestDto;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.response.RentPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.rentPayment.entity.RentPayment;
import blockchain.project.khu.apiserver.domain.rentPayment.enumerate.PaymentStatus;
import blockchain.project.khu.apiserver.domain.rentPayment.repository.RentPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RentPaymentService {

    private final RentRepository rentRepository;
    private final RentPaymentRepository rentPaymentRepository;
    private final PropertyRepository propertyRepository;

    @Transactional
    public RentPaymentResponseDto payRent(Long propertyId, Long currentUserId) {
        Rent rent = rentRepository.findByPropertyIdAndUserId(
                propertyId, currentUserId
        ).orElseThrow(() -> new IllegalArgumentException("해당 propertyId에 대한 임대 계약이 존재하지 않거나 본인의 계약이 아닙니다."));

        RentPayment payment = RentPayment.builder()
                .rent(rent)
                .amount(rent.getMonthlyRent())
                .paidAt(LocalDate.now())
                .status(PaymentStatus.PAID)
                .build();

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

    public List<RentPaymentResponseDto> getPaymentsByPropertyId(Long propertyId) {
        List<Rent> rents = rentRepository.findByPropertyId(propertyId);

        return rents.stream()
                .flatMap(rent -> rentPaymentRepository.findByRentId(rent.getId()).stream())
                .map(RentPaymentResponseDto::fromEntity)
                .toList();
    }

    public List<RentPaymentResponseDto> getMyPaymentsByPropertyId(Long propertyId, Long currentUserId) {
        List<Rent> rents = rentRepository.findByPropertyId(propertyId);

        return rents.stream()
                .filter(rent -> rent.getUser().getId().equals(currentUserId))
                .flatMap(rent -> rentPaymentRepository.findByRentId(rent.getId()).stream())
                .map(RentPaymentResponseDto::fromEntity)
                .toList();
    }

}
