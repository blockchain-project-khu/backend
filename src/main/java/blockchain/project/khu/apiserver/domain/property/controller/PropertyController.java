package blockchain.project.khu.apiserver.domain.property.controller;

import blockchain.project.khu.apiserver.common.apiPayload.success.SuccessApiResponse;
import blockchain.project.khu.apiserver.domain.property.dto.request.PropertyRequestDto;
import blockchain.project.khu.apiserver.domain.property.dto.response.PropertyResponseDto;
import blockchain.project.khu.apiserver.domain.property.service.PropertyService;
import com.sun.net.httpserver.Authenticator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
@RequiredArgsConstructor
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;

    // 등록
    @PostMapping()
    public SuccessApiResponse<PropertyResponseDto> createProperty(@RequestBody PropertyRequestDto dto) {
        PropertyResponseDto property = propertyService.createProperty(dto);
        return SuccessApiResponse.createProperty(property);
    }

    // 단건 조회
    @GetMapping("/{propertyId}")
    public SuccessApiResponse<PropertyResponseDto> getProperty(@PathVariable Long propertyId){
        log.info("propertyId: {}", propertyId);
        PropertyResponseDto property = propertyService.getProperty(propertyId);
        return SuccessApiResponse.getProperty(property);
    }

    // 목록 조회
    @GetMapping()
    public SuccessApiResponse<List<PropertyResponseDto>> getProperties() {
        List<PropertyResponseDto> properties = propertyService.getProperties();
        return SuccessApiResponse.getProperties(properties);
    }

    // 수정
    @PutMapping("/{propertyId}")
    public SuccessApiResponse<PropertyResponseDto> updateProperty(
            @PathVariable Long propertyId,
            @RequestBody PropertyRequestDto dto)
    {
        PropertyResponseDto responseDto = propertyService.updateProperty(propertyId, dto);
        return SuccessApiResponse.updateProperty(responseDto);
    }

    // 삭제
    @DeleteMapping("/{propertyId}")
    public SuccessApiResponse<Void> deleteProperty(@PathVariable Long propertyId){
        propertyService.deleteProperty(propertyId);
        return SuccessApiResponse.deleteProperty();
    }

    // [추가] 자기가 등록한 매물 리스트 조회
    @GetMapping("/sales")
    public ResponseEntity<List<PropertyResponseDto>> getSales(){
        return ResponseEntity.ok().body(
                propertyService.getSales()
        );
    }
}
