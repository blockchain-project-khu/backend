package blockchain.project.khu.apiserver.domain.rent.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RentStatus {
    ACTIVE("임대 중"),
    ENDED("종료됨"),
    CANCELLED("취소됨");

    private final String key;
}

