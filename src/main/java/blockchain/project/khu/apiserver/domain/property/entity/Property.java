package blockchain.project.khu.apiserver.domain.property.entity;

import blockchain.project.khu.apiserver.domain.property.dto.request.PropertyRequestDto;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "property")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "property_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;

    @Column(nullable = false)
    private String price;

    public void update(PropertyRequestDto requestDto){
        this.name = requestDto.getName();
        this.address = requestDto.getAddress();
        this.description = requestDto.getDescription();
        this.price = requestDto.getPrice();
    }
}
