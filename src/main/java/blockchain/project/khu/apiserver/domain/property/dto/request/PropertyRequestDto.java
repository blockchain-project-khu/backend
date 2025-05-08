package blockchain.project.khu.apiserver.domain.property.dto.request;


import blockchain.project.khu.apiserver.domain.property.entity.Property;
import blockchain.project.khu.apiserver.domain.property.entity.PropertyStatus;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;

@Data
@Builder
public class PropertyRequestDto {

    @NotBlank(message = "건물 이름은 비어있을 수 없습니다.")
    private String name;

    @NotBlank
    private String address;

    private String description;

    @NotBlank(message = "금액은 비어있을 수 없습니다.")
    private String price;

    public Property toEntity(User user) {
        return Property.builder()
                .name(name)
                .address(address)
                .description(description)
                .price(price)
                .user(user)
                .status(PropertyStatus.AVAILABLE)
                .build();
    }
}
