package blockchain.project.khu.apiserver.domain.property.dto.request;


import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.property.entity.PropertyStatus;
import blockchain.project.khu.apiserver.domain.property.entity.PropertyType;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PropertyRequestDto {

    @NotBlank(message = "건물 이름은 비어있을 수 없습니다.")
    private String name;

    @NotBlank
    private String address;

    private String description;

    @NotBlank(message = "가격은 비어있을 수 없습니다.")
    private String price;

    @NotBlank(message = "월 임대료는 비어있을 수 없습니다.")
    private BigDecimal monthlyRent;

    @NotBlank(message = "전용 면적은 비어있을 수 없습니다.")
    private float supplyArea;

    @NotBlank(message = "층 수는 비어있을 수 없습니다.")
    private String totalFloors;

    private String imageUrl;

    private PropertyType propertyType;

    public Property toEntity(User user, Long propertyId) {
        return Property.builder()
                .id(propertyId)
                .name(name)
                .address(address)
                .description(description)
                .price(price)
                .user(user)
                .status(PropertyStatus.AVAILABLE)
                .propertyType(propertyType)
                .monthlyRent(monthlyRent)
                .supplyArea(supplyArea)
                .totalFloors(totalFloors)
                .imageUrl(imageUrl)
                .build();
    }
}
