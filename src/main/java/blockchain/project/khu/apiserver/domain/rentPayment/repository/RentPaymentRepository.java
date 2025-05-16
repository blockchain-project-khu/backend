package blockchain.project.khu.apiserver.domain.rentPayment.repository;

import blockchain.project.khu.apiserver.domain.rentPayment.entity.RentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentPaymentRepository extends JpaRepository<RentPayment, Long> {
}
