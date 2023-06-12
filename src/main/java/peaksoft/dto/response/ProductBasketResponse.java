package peaksoft.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductBasketResponse {
    private Long id;
    private String name;
}