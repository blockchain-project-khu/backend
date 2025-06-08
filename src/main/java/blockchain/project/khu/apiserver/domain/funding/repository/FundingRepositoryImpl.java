package blockchain.project.khu.apiserver.domain.funding.repository;

import blockchain.project.khu.apiserver.domain.funding.dto.response.FundingResponseDto;
import blockchain.project.khu.apiserver.domain.funding.dto.response.QFundingResponseDto;
import blockchain.project.khu.apiserver.domain.funding.entity.FundingStatus;
import blockchain.project.khu.apiserver.domain.funding.entity.QFunding;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FundingRepositoryImpl implements FundingRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    private final QFunding f = QFunding.funding;

    @Override
    public List<FundingResponseDto> findMyFundingsByStatus(Long userId, FundingStatus status) {
        return jpaQueryFactory
                .select(new QFundingResponseDto(
                        f.id,
                        f.percentage,
                        f.status,
                        f.user.id,
                        f.property.id
                ))
                .from(f)
                .where(
                        f.user.id.eq(userId),
                        status != null ? f.status.eq(status) : null
                )
                .fetch();
    }

    @Override
    public List<FundingResponseDto> findAllByPropertyId(Long propertyId, FundingStatus status) {
        return jpaQueryFactory
                .select(new QFundingResponseDto(
                        f.id,
                        f.percentage,
                        f.status,
                        f.user.id,
                        f.property.id
                ))
                .from(f)
                .where(
                        f.property.id.eq(propertyId),
                        status != null ? f.status.eq(status) : null
                )
                .fetch();
    }
}
