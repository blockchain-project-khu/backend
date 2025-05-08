package blockchain.project.khu.apiserver.domain.funding.controller;

import blockchain.project.khu.apiserver.common.apiPayload.success.SuccessApiResponse;
import blockchain.project.khu.apiserver.domain.funding.dto.request.FundingRequestDto;
import blockchain.project.khu.apiserver.domain.funding.dto.response.FundingResponseDto;
import blockchain.project.khu.apiserver.domain.funding.entity.FundingStatus;
import blockchain.project.khu.apiserver.domain.funding.service.FundingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthColorChooserUI;
import java.util.List;

@RestController
@RequestMapping("/api/fundings")
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    // 펀딩 등록
    @PostMapping("/properties/{propertyId}")
    public SuccessApiResponse<Long> createFunding(
            @PathVariable Long propertyId,
            @RequestBody FundingRequestDto requestDto
    ){
        Long fundingId = fundingService.createFunding(propertyId, requestDto);
        return SuccessApiResponse.createFunding(fundingId);
    }

    // 펀딩 단건 조회
    @GetMapping("/{fundingId}")
    public SuccessApiResponse<FundingResponseDto> getFunding(
            @PathVariable Long fundingId
    ){
        FundingResponseDto responseDto = fundingService.getFunding(fundingId);
        return SuccessApiResponse.getFunding(responseDto);
    }

    // 펀딩 목록 조회 - 구매자
    @GetMapping("/me")
    public SuccessApiResponse<List<FundingResponseDto>> getUserFundingList(
            @RequestParam(required = false) FundingStatus status
    ){
        List<FundingResponseDto> fundingList = fundingService.getFundingList(status);
        return SuccessApiResponse.getFunding(fundingList);
    }

    // 펀딩 목록 조회 - 판매자가 property 별로 목록 조회
    @GetMapping("/property/{propertyId}")
    public SuccessApiResponse<List<FundingResponseDto>> getFundingListByProperty(
            @PathVariable Long propertyId,
            @RequestParam(required = false) FundingStatus status
    ){
        List<FundingResponseDto> fundingListByProperty = fundingService.getFundingListByProperty(propertyId, status);
        return SuccessApiResponse.getFunding(fundingListByProperty);
    }

    // 펀딩 수정 - 구매자
    @PutMapping("/{fundingId}")
    public SuccessApiResponse<FundingResponseDto> updateFunding(
            @PathVariable Long fundingId,
            @RequestBody FundingRequestDto requestDto
    ){
        FundingResponseDto responseDto = fundingService.updateFunding(fundingId, requestDto);
        return SuccessApiResponse.updateFunding(responseDto);
    }

    // 펀딩 상태 수정 - 판매자
    @PutMapping("/{fundingId}/status")
    public SuccessApiResponse<FundingResponseDto> updateFundingStatus(
            @PathVariable Long fundingId,
            @RequestParam FundingStatus status
    ){
        FundingResponseDto responseDto = fundingService.updateFundingStatus(fundingId, status);
        return SuccessApiResponse.updateFunding(responseDto);
    }

    // 펀딩 삭제
    @DeleteMapping("/{fundingId}")
    public SuccessApiResponse<Void> deleteFunding(
            @PathVariable Long fundingId
    ){
        fundingService.deleteFunding(fundingId);
        return SuccessApiResponse.deleteFunding();
    }
}
