package blockchain.project.khu.apiserver.common.apiPayload.success;

import blockchain.project.khu.apiserver.common.apiPayload.BaseApiResponse;
import blockchain.project.khu.apiserver.domain.funding.dto.response.FundingResponseDto;
import blockchain.project.khu.apiserver.domain.property.dto.response.PropertyPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.property.dto.response.PropertyResponseDto;
import blockchain.project.khu.apiserver.domain.funding.dto.response.FundingIncomeResponseDto;
import blockchain.project.khu.apiserver.domain.rentPayment.dto.response.RentPaymentResponseDto;
import blockchain.project.khu.apiserver.domain.user.dto.response.LoginResponse;
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

    public static SuccessApiResponse<List<PropertyResponseDto>> getSales(List<PropertyResponseDto> responseDtoList) {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "판매중인 매물 조회 성공", responseDtoList);
    }

    // [FUNDING]
    public static SuccessApiResponse<Long> createFunding(Long fundingId) {
        return new SuccessApiResponse<>(true, HttpStatus.CREATED.toString(), "펀딩 등록 성공", fundingId);
    }

    public static SuccessApiResponse<FundingResponseDto> getFunding(FundingResponseDto responseDto) {
        return new SuccessApiResponse<>(true, HttpStatus.FOUND.toString(), "펀딩 조회 성공", responseDto);
    }

    public static SuccessApiResponse<List<FundingResponseDto>> getFunding(List<FundingResponseDto> responseDtoList) {
        return new SuccessApiResponse<>(true, HttpStatus.FOUND.toString(), "펀딩 조회 성공", responseDtoList);
    }

    public static SuccessApiResponse<FundingResponseDto> updateFunding(FundingResponseDto responseDto) {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "펀딩 수정 성공", responseDto);
    }

    public static SuccessApiResponse<Void> deleteFunding() {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "펀딩 삭제 성공", null);
    }

    // [RENT]
    public static SuccessApiResponse<List<PropertyResponseDto>> getRent(List<PropertyResponseDto> responseDtoList) {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "본인 임대 계약 조회 성공", responseDtoList);
    }

    // [RENT-PAYMENT]
    public static SuccessApiResponse<RentPaymentResponseDto> payRent(RentPaymentResponseDto responseDto) {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "월세 납부 성공", responseDto);
    }

    public static SuccessApiResponse<List<RentPaymentResponseDto>> getMyPayments(List<RentPaymentResponseDto> result) {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "월세 납부 내역 조회 성공", result);
    }

    public static SuccessApiResponse<List<PropertyPaymentResponseDto>> getPaymentsByProperty(List<PropertyPaymentResponseDto> result) {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "등록한 매물별 월세 조회 성공", result);
    }

    public static SuccessApiResponse<List<RentPaymentResponseDto>> getPaymentsByPropertyId(List<RentPaymentResponseDto> result) {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "특정 매물 월세 납부 내역 조회 성공", result);
    }

    public static SuccessApiResponse<List<RentPaymentResponseDto>> getMyPaymentsByPropertyId(List<RentPaymentResponseDto> result) {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "특정 매물 별 나의 월세 납부 내역 조회 성공", result);
    }

    public static SuccessApiResponse<List<FundingIncomeResponseDto>> getMyFundingIncome(List<FundingIncomeResponseDto> result) {
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString(), "펀딩한 매물에 대해 월세 수익 리스트 조회 성공", result);
    }
}