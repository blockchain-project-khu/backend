package blockchain.project.khu.apiserver.domain.property.dto.response;

import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.property.entity.PropertyStatus;
import blockchain.project.khu.apiserver.domain.property.entity.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PropertyResponseDto {
    private Long id;
    private Long userId;
    private String name;
    private String address;
    private String description;
    private PropertyStatus status;
    private PropertyType type;
    private String price;
    private BigDecimal monthlyRent;
    private float supplyArea;
    private String totalFloors;
    private String imageUrl;
    private Integer currentFundingPercent ;

    public static PropertyResponseDto fromEntity(Property property){
        return PropertyResponseDto.builder()
                .id(property.getId())
                .userId(property.getUser().getId())
                .name(property.getName())
                .address(property.getAddress())
                .description(property.getDescription())
                .status(property.getStatus())
                .type(property.getPropertyType())
                .price(property.getPrice())
                .monthlyRent(property.getMonthlyRent())
                .supplyArea(property.getSupplyArea())
                .totalFloors(property.getTotalFloors())
                .imageUrl(property.getImageUrl())
                .currentFundingPercent(property.getCurrentFundingPercent())
                .build();
    }
}
