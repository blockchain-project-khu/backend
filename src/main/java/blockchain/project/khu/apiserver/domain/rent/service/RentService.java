package blockchain.project.khu.apiserver.domain.rent.service;

import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.property.repository.PropertyRepository;
import blockchain.project.khu.apiserver.domain.rent.dto.request.RentRequestDto;
import blockchain.project.khu.apiserver.domain.rent.entity.Rent;
import blockchain.project.khu.apiserver.domain.rent.enumerate.RentStatus;
import blockchain.project.khu.apiserver.domain.rent.repository.RentRepository;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import blockchain.project.khu.apiserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public Rent createRent(RentRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매물입니다."));

        Rent rent = Rent.builder()
                .tenant(user)
                .property(property)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .monthlyRent(dto.getMonthlyRent())
                .deposit(dto.getDeposit())
                .paymentDay(dto.getPaymentDay())
                .status(RentStatus.ACTIVE)
                .build();

        return rentRepository.save(rent);
    }
}
