package blockchain.project.khu.apiserver.domain.rent.dto.response;

import blockchain.project.khu.apiserver.domain.rent.entity.Rent;
import blockchain.project.khu.apiserver.domain.rent.enumerate.RentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentResponseDto {

    public record Detail(
            Long rentId,
            Long userId,
            String username,
            Long propertyId,
            Long propertyOwnerId,
            String propertyOwnerName,
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            BigDecimal monthlyRent,
            BigDecimal deposit,
            RentStatus status,
            Integer paymentDay
            ){
        public static Detail from(Rent rent) {
            return new Detail(
                    rent.getId(),
                    rent.getUser().getId(),
                    rent.getUser().getUsername(),
                    rent.getProperty().getId(),
                    rent.getProperty().getUser().getId(),
                    rent.getProperty().getUser().getUsername(),
                    rent.getStartDate(),
                    rent.getEndDate(),
                    rent.getMonthlyRent(),
                    rent.getDeposit(),
                    rent.getStatus(),
                    rent.getPaymentDay()
            );
        }
    }
}
