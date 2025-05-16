package blockchain.project.khu.apiserver.domain.rent.repository;

import blockchain.project.khu.apiserver.domain.rent.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findByUserId(Long userId);
    List<Rent> findByPropertyId(Long propertyId);
}