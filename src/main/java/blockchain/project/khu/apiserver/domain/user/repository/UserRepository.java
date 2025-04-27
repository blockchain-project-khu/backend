package blockchain.project.khu.apiserver.domain.user.repository;

import blockchain.project.khu.apiserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
