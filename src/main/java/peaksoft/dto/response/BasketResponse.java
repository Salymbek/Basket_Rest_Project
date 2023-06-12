package peaksoft.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponse{
        private Long id;
        private String user;
        private List<ProductBasketResponse> products;
        private Long countOfProducts;
        private BigDecimal grandTotal;

}
