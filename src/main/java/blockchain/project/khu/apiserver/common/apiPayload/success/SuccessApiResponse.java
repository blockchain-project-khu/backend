package blockchain.project.khu.apiserver.common.apiPayload.success;

import blockchain.project.khu.apiserver.common.apiPayload.BaseApiResponse;
import blockchain.project.khu.apiserver.domain.property.dto.response.PropertyResponseDto;
import blockchain.project.khu.apiserver.domain.user.dto.response.LoginResponse;
import com.sun.net.httpserver.Authenticator;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class SuccessApiResponse <T> extends BaseApiResponse {
    private final T response;

    public SuccessApiResponse(Boolean isSuccess, String code, String message, T response) {
        super(isSuccess, code, message);
        this.response = response;
    }

    // [AUTH]
    public static SuccessApiResponse<Void> Register(){
        return new SuccessApiResponse<>(true, HttpStatus.CREATED.toString()
                , "회원가입 성공", null);
    }
    public static SuccessApiResponse<LoginResponse> Login(LoginResponse response){
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString()
                , "로그인 성공", response);
    }
    public static SuccessApiResponse<Void> ReissueToken(){
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString()
                , "토큰 재발급 성공", null);
    }

    // [PROPERTY]
    public static SuccessApiResponse<PropertyResponseDto> createProperty(PropertyResponseDto responseDto) {
        return new SuccessApiResponse<>(true, HttpStatus.CREATED.toString(), "매물 등록 성공", responseDto);
    }

    public static SuccessApiResponse<PropertyResponseDto> getProperty(PropertyResponseDto responseDto) {
        return new SuccessApiResponse<>(true, HttpStatus.FOUND.toString(), "매물 조회 성공", responseDto);
    }

    public static SuccessApiResponse<List<PropertyResponseDto>> getProperties(List<PropertyResponseDto> responseDtoList) {
        return new SuccessApiResponse<>(true, HttpStatus.FOUND.toString(), "매물 조회 성공", responseDtoList);
    }

    public static SuccessApiResponse<PropertyResponseDto> updateProperty(PropertyResponseDto responseDto) {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "매물 수정 성공", responseDto);
    }

    public static SuccessApiResponse<Void> deleteProperty() {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "매물 삭제 성공", null);
    }
}