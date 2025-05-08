package blockchain.project.khu.apiserver.domain.property.repository;

import blockchain.project.khu.apiserver.domain.property.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
