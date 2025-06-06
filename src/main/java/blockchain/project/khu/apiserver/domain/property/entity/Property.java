package blockchain.project.khu.apiserver.domain.property.entity;

import blockchain.project.khu.apiserver.domain.funding.entity.Funding;
import blockchain.project.khu.apiserver.domain.property.dto.request.PropertyRequestDto;
import blockchain.project.khu.apiserver.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private BigDecimal monthlyRent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType propertyType;

    @Column(nullable = false)
    private float supplyArea;

    @Column(nullable = false)
    private String totalFloors;

    @Column(nullable = false)
    private String imageUrl;

    // 지금까지 모인 퍼센트를 캐시 (0 ~ 100)
    @Column(nullable = false)
    private Integer currentFundingPercent = 0;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Funding> fundings = new ArrayList<>();

    public void update(PropertyRequestDto requestDto){
        this.name = requestDto.getName();
        this.address = requestDto.getAddress();
        this.description = requestDto.getDescription();
        this.price = requestDto.getPrice();
        this.monthlyRent = requestDto.getMonthlyRent();
        this.supplyArea = requestDto.getSupplyArea();
        this.totalFloors = requestDto.getTotalFloors();
        this.imageUrl = requestDto.getImageUrl();
    }

    // fundings 목록에 엔티티를 추가
    public void addFundingEntity(Funding funding) {
        fundings.add(funding);
        int newSum = this.currentFundingPercent + funding.getPercentage();
        this.currentFundingPercent = Math.min(newSum, 100);
    }
}
