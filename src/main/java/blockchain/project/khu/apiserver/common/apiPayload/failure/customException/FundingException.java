package blockchain.project.khu.apiserver.common.apiPayload.failure.customException;

public class FundingException {
    public static class FundingNotFoundException extends RuntimeException {
        public FundingNotFoundException() {
            super("존재하지 않는 매물입니다.");
        }
    }

    public static class FundingInvalidInputException extends RuntimeException {
        public FundingInvalidInputException() {
            super("잘못된 매물 요청입니다.");
        }
    }

    public static class FundingDuplicateException extends RuntimeException {
        public FundingDuplicateException() {
            super("이미 매물이 존재합니다.");
        }
    }

    public static class FundingAlreadySuccessException extends RuntimeException {
        public FundingAlreadySuccessException() {
            super("이미 펀딩이 완료된 매물입니다.");
        }
    }

    public static class UnauthorizedAccessException extends RuntimeException {}

    public static class FundingAlreadyAppliedException extends RuntimeException {
        public FundingAlreadyAppliedException() {
            super("이미 해당 매물에 펀딩을 신청하였습니다.");
        }
    }

    public static class FundingClosedException extends RuntimeException {
        public FundingClosedException() {
            super("해당 매물의 펀딩은 마감되었습니다.");
        }
    }

    public static class FundingCancelNotAllowedException extends RuntimeException {
        public FundingCancelNotAllowedException() {
            super("해당 펀딩은 취소할 수 없습니다.");
        }
    }

    public static class InternalServerErrorException extends RuntimeException {
        public InternalServerErrorException() {
            super("서버 내부 오류가 발생했습니다.");
        }
    }
}
