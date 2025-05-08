package blockchain.project.khu.apiserver.common.apiPayload.failure;

import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.FundingException;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.JWTException;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.PropertyException;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customException.UserException;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customExceptionStatus.FundingExceptionStatus;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customExceptionStatus.JWTExceptionStatus;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customExceptionStatus.PropertyExceptionStatus;
import blockchain.project.khu.apiserver.common.apiPayload.failure.customExceptionStatus.UserExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // [JWT]
    @ExceptionHandler(JWTException.TokenNullException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(JWTException.TokenNullException e){
        log.error("[GlobalExceptionHandler] JWTException.TokenNullException occurred");
        return ResponseEntity
                .status(
                        JWTExceptionStatus.TOKEN_NULL.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, JWTExceptionStatus.TOKEN_NULL.getCode(), JWTExceptionStatus.TOKEN_NULL.getMessage()
                        )
                );
    }

    @ExceptionHandler(JWTException.TokenExpiredException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(JWTException.TokenExpiredException e){
        log.error("[GlobalExceptionHandler] JWTException.TokenExpiredException occurred");
        return ResponseEntity
                .status(
                        JWTExceptionStatus.TOKEN_EXPIRED.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, JWTExceptionStatus.TOKEN_EXPIRED.getCode(), JWTExceptionStatus.TOKEN_EXPIRED.getMessage()
                        )
                );
    }

    @ExceptionHandler(JWTException.TokenTypeNotAccessException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(JWTException.TokenTypeNotAccessException e){
        log.error("[GlobalExceptionHandler] JWTException.TokenTypeNotAccessException occurred");
        return ResponseEntity
                .status(
                        JWTExceptionStatus.TOKEN_TYPE_NOT_ACCESS.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, JWTExceptionStatus.TOKEN_TYPE_NOT_ACCESS.getCode(), JWTExceptionStatus.TOKEN_TYPE_NOT_ACCESS.getMessage()
                        )
                );
    }


    // [AUTH]
    @ExceptionHandler(UserException.UsernameAlreadyExistException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(UserException.UsernameAlreadyExistException e){
        log.error("[GlobalExceptionHandler] UserException.UsernameAlreadyExistException occurred");
        return ResponseEntity
                .status(
                        UserExceptionStatus.USERNAME_ALREADY_EXIST.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, UserExceptionStatus.USERNAME_ALREADY_EXIST.getCode(), UserExceptionStatus.USERNAME_ALREADY_EXIST.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserException.PasswordNotValidException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(UserException.PasswordNotValidException e){
        log.error("[GlobalExceptionHandler] UserException.PasswordNotValidException occurred");
        return ResponseEntity
                .status(
                        UserExceptionStatus.PASSWORD_NOT_VALID.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, UserExceptionStatus.PASSWORD_NOT_VALID.getCode(), UserExceptionStatus.PASSWORD_NOT_VALID.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserException.TokenNotValidException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(UserException.TokenNotValidException e){
        log.error("[GlobalExceptionHandler] UserException.TokenNotValidException occurred");
        return ResponseEntity
                .status(
                        UserExceptionStatus.TOKEN_NOT_VALID.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, UserExceptionStatus.TOKEN_NOT_VALID.getCode(), UserExceptionStatus.TOKEN_NOT_VALID.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserException.UsernameNotExistException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(UserException.UsernameNotExistException e){
        log.error("[GlobalExceptionHandler] UserException.UsernameNotExistException occurred");
        return ResponseEntity
                .status(
                        UserExceptionStatus.USERNAME_NOT_EXIST.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, UserExceptionStatus.USERNAME_NOT_EXIST.getCode(), UserExceptionStatus.USERNAME_NOT_EXIST.getMessage()
                        )
                );
    }

    // [PROPERTY]
    @ExceptionHandler(PropertyException.PropertyNotFound.class)
    public ResponseEntity<ExceptionApiResponse> handleException(PropertyException.PropertyNotFound e){
        log.error("[GlobalExceptionHandler PropertyException.PROPERTY_NOT_FOUND occurred");
        return ResponseEntity
                .status(
                        PropertyExceptionStatus.PROPERTY_NOT_FOUND.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, PropertyExceptionStatus.PROPERTY_NOT_FOUND.getCode(), PropertyExceptionStatus.PROPERTY_NOT_FOUND.getMessage()
                        )
                );
    }

    @ExceptionHandler(PropertyException.PropertyInvalidInputException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(PropertyException.PropertyInvalidInputException e){
        log.error("[GlobalExceptionHandler PropertyException.PropertyInvalidInputException occurred");
        return ResponseEntity
                .status(
                        PropertyExceptionStatus.PROPERTY_INVALID_INPUT.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, PropertyExceptionStatus.PROPERTY_INVALID_INPUT.getCode(), PropertyExceptionStatus.PROPERTY_INVALID_INPUT.getMessage()
                        )
                );
    }

    // [FUNDING]
    @ExceptionHandler(FundingException.FundingNotFoundException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(FundingException.FundingNotFoundException e){
        log.error("[GlobalExceptionHandler FundingException.FUNDING_NOT_FOUND occurred");
        return ResponseEntity
                .status(
                        FundingExceptionStatus.FUNDING_NOT_FOUND.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, FundingExceptionStatus.FUNDING_NOT_FOUND.getCode(), FundingExceptionStatus.FUNDING_NOT_FOUND.getMessage()
                        )
                );
    }

    @ExceptionHandler(FundingException.UnauthorizedAccessException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(FundingException.UnauthorizedAccessException e){
        log.error("[GlobalExceptionHandler FundingException.FUNDING_UNAUTHORIZED_ACCESS occurred");
        return ResponseEntity
                .status(
                        FundingExceptionStatus.FUNDING_UNAUTHORIZED_ACCESS.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, FundingExceptionStatus.FUNDING_UNAUTHORIZED_ACCESS.getCode(), FundingExceptionStatus.FUNDING_UNAUTHORIZED_ACCESS.getMessage()
                        )
                );
    }

    @ExceptionHandler(FundingException.FundingDuplicateException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(FundingException.FundingDuplicateException e){
        log.error("[GlobalExceptionHandler] FundingException.FUNDING_DUPLICATE occurred");
        return ResponseEntity
                .status(FundingExceptionStatus.FUNDING_DUPLICATE.getHttpStatus())
                .body(new ExceptionApiResponse(
                        false,
                        FundingExceptionStatus.FUNDING_DUPLICATE.getCode(),
                        FundingExceptionStatus.FUNDING_DUPLICATE.getMessage()
                ));
    }


    // [FUNDING - 이미 신청된 펀딩]
    @ExceptionHandler(FundingException.FundingAlreadyAppliedException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(FundingException.FundingAlreadyAppliedException e){
        log.error("[GlobalExceptionHandler] FundingException.FUNDING_ALREADY_APPLIED occurred");
        return ResponseEntity
                .status(FundingExceptionStatus.FUNDING_ALREADY_APPLIED.getHttpStatus())
                .body(new ExceptionApiResponse(
                        false,
                        FundingExceptionStatus.FUNDING_ALREADY_APPLIED.getCode(),
                        FundingExceptionStatus.FUNDING_ALREADY_APPLIED.getMessage()
                ));
    }

    // [FUNDING - 이미 완료된 펀딩]
    @ExceptionHandler(FundingException.FundingAlreadySuccessException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(FundingException.FundingAlreadySuccessException e){
        log.error("[GlobalExceptionHandler] FundingException.FUNDING_ALREADY_SUCCESS occurred");
        return ResponseEntity
                .status(FundingExceptionStatus.FUNDING_ALREADY_SUCCESS.getHttpStatus())
                .body(new ExceptionApiResponse(
                        false,
                        FundingExceptionStatus.FUNDING_ALREADY_SUCCESS.getCode(),
                        FundingExceptionStatus.FUNDING_ALREADY_SUCCESS.getMessage()
                ));
    }

    // [FUNDING - 펀딩 마감 상태]
    @ExceptionHandler(FundingException.FundingClosedException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(FundingException.FundingClosedException e){
        log.error("[GlobalExceptionHandler] FundingException.FUNDING_CLOSED occurred");
        return ResponseEntity
                .status(FundingExceptionStatus.FUNDING_CLOSED.getHttpStatus())
                .body(new ExceptionApiResponse(
                        false,
                        FundingExceptionStatus.FUNDING_CLOSED.getCode(),
                        FundingExceptionStatus.FUNDING_CLOSED.getMessage()
                ));
    }

    // [FUNDING - 펀딩 취소 불가 상태]
    @ExceptionHandler(FundingException.FundingCancelNotAllowedException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(FundingException.FundingCancelNotAllowedException e){
        log.error("[GlobalExceptionHandler] FundingException.FUNDING_CANCEL_NOT_ALLOWED occurred");
        return ResponseEntity
                .status(FundingExceptionStatus.FUNDING_CANCEL_NOT_ALLOWED.getHttpStatus())
                .body(new ExceptionApiResponse(
                        false,
                        FundingExceptionStatus.FUNDING_CANCEL_NOT_ALLOWED.getCode(),
                        FundingExceptionStatus.FUNDING_CANCEL_NOT_ALLOWED.getMessage()
                ));
    }

}