package blockchain.project.khu.apiserver.domain.rentPayment.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    PAID("납부 완료"),
    LATE("연체"),
    UNPAID("미납");

    private final String key;
}
