package blockchain.project.khu.apiserver.domain.funding.repository;

import blockchain.project.khu.apiserver.domain.funding.entity.Funding;
import blockchain.project.khu.apiserver.domain.funding.entity.FundingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundingRepository extends JpaRepository<Funding, Long> ,FundingRepositoryCustom{
    Boolean existsByUserIdAndPropertyId(Long userId, Long propertyId);
    List<Funding> findByUserIdAndStatus(Long userId, FundingStatus status);
}
