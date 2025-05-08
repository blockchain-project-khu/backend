package blockchain.project.khu.apiserver.domain.property.dto.response;

import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.property.entity.PropertyStatus;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class PropertyResponseDto {
    private Long id;
    private Long userId;
    private String name;
    private String address;
    private String description;
    private PropertyStatus status;
    private String price;

    public static PropertyResponseDto fromEntity(Property property){
        return PropertyResponseDto.builder()
                .id(property.getId())
                .userId(property.getUser().getId())
                .name(property.getName())
                .address(property.getAddress())
                .description(property.getDescription())
                .status(property.getStatus())
                .price(property.getPrice())
                .build();
    }
}
