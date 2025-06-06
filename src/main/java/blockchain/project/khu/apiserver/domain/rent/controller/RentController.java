package blockchain.project.khu.apiserver.domain.rent.controller;

import blockchain.project.khu.apiserver.domain.rent.dto.request.RentRequestDto;
import blockchain.project.khu.apiserver.domain.rent.dto.response.RentResponseDto;
import blockchain.project.khu.apiserver.domain.rent.entity.Rent;
import blockchain.project.khu.apiserver.domain.rent.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @PostMapping
    public ResponseEntity<RentResponseDto.Detail> createRent(@RequestBody RentRequestDto dto) {
        Rent rent = rentService.createRent(dto);
        return ResponseEntity.ok(RentResponseDto.Detail.from(rent));
    }
}
