package peaksoft.dto.response;

import lombok.*;
import peaksoft.model.Product;
import peaksoft.model.User;

import java.math.BigDecimal;
import java.util.List;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponse{
        private Long id;
        private String name;
        private int amount;
        private BigDecimal sum;

}
