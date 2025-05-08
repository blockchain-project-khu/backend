package blockchain.project.khu.apiserver.common.apiPayload.failure.customExceptionStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FundingExceptionStatus {
    FUNDING_NOT_FOUND      (HttpStatus.NOT_FOUND,    "FUNDING_NOT_FOUND",      "존재하지 않는 매물입니다."),
    FUNDING_INVALID_INPUT  (HttpStatus.BAD_REQUEST,  "FUNDING_INVALID_INPUT",  "잘못된 매물 요청입니다."),
    FUNDING_DUPLICATE      (HttpStatus.FORBIDDEN,    "FUNDING_DUPLICATE",      "이미 매물이 존재합니다."),
    FUNDING_ALREADY_SUCCESS  (HttpStatus.BAD_REQUEST, "FUNDING_ALREADY_SUCCESS",  "이미 펀딩이 완료된 매물입니다."),
    FUNDING_ALREADY_APPLIED  (HttpStatus.CONFLICT,    "FUNDING_ALREADY_APPLIED",  "이미 해당 매물에 펀딩을 신청하였습니다."),
    FUNDING_CLOSED           (HttpStatus.BAD_REQUEST, "FUNDING_CLOSED",           "해당 매물의 펀딩은 마감되었습니다."),
    FUNDING_CANCEL_NOT_ALLOWED (HttpStatus.FORBIDDEN, "FUNDING_CANCEL_NOT_ALLOWED", "해당 펀딩은 취소할 수 없습니다."),
    FUNDING_UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "FUNDING_UNAUTHORIZED_ACCESS", "펀딩에 대한 접근 권한이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
