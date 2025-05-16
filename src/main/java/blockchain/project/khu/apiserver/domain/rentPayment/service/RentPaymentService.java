package blockchain.project.khu.apiserver.domain.rentPayment.service;

import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.RentException;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RentPaymentService {

    private final RentRepository rentRepository;
    private final RentPaymentRepository rentPaymentRepository;

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
}
