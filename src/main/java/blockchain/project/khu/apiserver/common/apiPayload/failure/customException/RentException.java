package blockchain.project.khu.apiserver.common.apiPayload.failure.customException;

public class RentException {

    public static class RentNotFoundException extends RuntimeException {
        public RentNotFoundException() {
            super("존재하지 않는 계약입니다.");
        }
    }

    public static class InvalidRentStateException extends RuntimeException {
        public InvalidRentStateException() {
            super("계약 상태가 유효하지 않습니다.");
        }
    }
}
