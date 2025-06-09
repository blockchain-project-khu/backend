package blockchain.project.khu.apiserver.domain.rent.controller;

import blockchain.project.khu.apiserver.common.annotation.CurrentUser;
import blockchain.project.khu.apiserver.common.apiPayload.success.SuccessApiResponse;
import blockchain.project.khu.apiserver.domain.property.dto.response.PropertyResponseDto;
import blockchain.project.khu.apiserver.domain.rent.dto.request.RentRequestDto;
import blockchain.project.khu.apiserver.domain.rent.dto.response.RentResponseDto;
import blockchain.project.khu.apiserver.domain.rent.entity.Rent;
import blockchain.project.khu.apiserver.domain.rent.service.RentService;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @PostMapping
    @Operation(summary = "특정 매물에 대한 임대 계약 생성", description = "특정 매물에 대한 임대 계약을 생성합니다. 계약에 관한 모든 정보를 요청으로 받아 처리 후 생성된 계약 정보를 반환합니다.")
    public ResponseEntity<RentResponseDto.Detail> createRent(
            @RequestBody RentRequestDto dto,
            @Parameter(hidden = true) @CurrentUser User user
    ) {
        Rent rent = rentService.createRent(dto, user.getId());
        return ResponseEntity.ok(RentResponseDto.Detail.from(rent));
    }

    @GetMapping
    @Operation(summary = "로그인한 사용자의 임대한 건물 리스트 조회", description = "로그인한 사용자의 임대한 건물 리스트를 조회합니다.")
    public SuccessApiResponse<List<PropertyResponseDto>> getRent(
            @Parameter(hidden = true) @CurrentUser User currentUser
    ) {
        return SuccessApiResponse.getRent(rentService.getRentPropertyList(currentUser.getId()));
    }
}
