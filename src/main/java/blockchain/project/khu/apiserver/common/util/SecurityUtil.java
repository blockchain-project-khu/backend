package blockchain.project.khu.apiserver.common.util;

import blockchain.project.khu.apiserver.common.apiPayload.failure.customExceptionStatus.JWTExceptionStatus;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customExceptionStatus.UserExceptionStatus;
import blockchain.project.khu.apiserver.domain.user.dto.userDetails.RoleUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    public static Long getCurrentMemberId() {

        // Spring Security가 현재 요청을 처리하는 스레드의 인증 정보를 담고 있는 컨텍스트
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication: {}", authentication);

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new SecurityException(UserExceptionStatus.USERNAME_NOT_EXIST.getMessage());
        }

        // principal은 로그인한 사용자의 정보 객체
        //JWT 기반 인증에서는 일반적으로 CustomUserDetails 혹은 String(ID 값) 형태로 존재
        Object principal = authentication.getPrincipal();

        if (principal instanceof RoleUserDetails) {
            return ((RoleUserDetails) principal).getUserId();
        } else if (principal instanceof String) {
            try {
                return Long.valueOf((String) principal);
            } catch (NumberFormatException e) {
                throw new SecurityException(UserExceptionStatus.AUTHENTICATION_NOT_FOUND.getMessage());
            }
        } else {
            throw new SecurityException(UserExceptionStatus.UNKNOWN_PRINCIPAL_TYPE.getMessage());
        }
    }
}