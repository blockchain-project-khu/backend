package blockchain.project.khu.apiserver.common.apiPayload.failure.customExceptionStatus;

import blockchain.project.khu.apiserver.common.apiPayload.BaseApiResponse;
import blockchain.project.khu.apiserver.common.apiPayload.failure.ExceptionApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PropertyExceptionStatus{

    PROPERTY_NOT_FOUND      (HttpStatus.NOT_FOUND,    "PROPERTY_NOT_FOUND",      "존재하지 않는 매물입니다."),
    PROPERTY_INVALID_INPUT  (HttpStatus.BAD_REQUEST,  "PROPERTY_INVALID_INPUT",  "잘못된 매물 요청입니다."),
    PROPERTY_DUPLICATE      (HttpStatus.FORBIDDEN,    "PROPERTY_DUPLICATE",      "이미 매물이 존재합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
