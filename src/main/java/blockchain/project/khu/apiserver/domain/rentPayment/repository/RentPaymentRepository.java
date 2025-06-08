package blockchain.project.khu.apiserver.domain.rentPayment.repository;

import blockchain.project.khu.apiserver.domain.rentPayment.entity.RentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentPaymentRepository extends JpaRepository<RentPayment, Long> {
    List<RentPayment> findByRentId(Long id);
    List<RentPayment> findByRent_Property_Id(Long propertyId);
}
