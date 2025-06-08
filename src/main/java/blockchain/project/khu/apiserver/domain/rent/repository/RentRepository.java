package blockchain.project.khu.apiserver.domain.rent.repository;

import blockchain.project.khu.apiserver.domain.rent.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findByUserId(Long userId);
    List<Rent> findByPropertyId(Long propertyId);
    Optional<Rent> findByPropertyIdAndUserId(Long propertyId, Long currentUserId);
}