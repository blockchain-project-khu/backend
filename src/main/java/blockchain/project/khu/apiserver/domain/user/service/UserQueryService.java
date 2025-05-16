package blockchain.project.khu.apiserver.domain.user.service;

import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.UserException;
import blockchain.project.khu.apiserver.domain.user.dto.request.LoginRequest;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import blockchain.project.khu.apiserver.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserQueryService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String checkUserInfo(LoginRequest loginRequest){
        log.info("[UserQueryService - login]");

        // username 확인
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(UserException.UsernameNotExistException::new);

        // password 확인
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new UserException.PasswordNotValidException();
        }

        return user.getUsername();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 ID: " + userId));
    }
}
