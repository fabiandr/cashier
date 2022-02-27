package org.nextail.domain.discount;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Discount {

    private String productCode;
    private int requiredQuantity;
    private int maxQuantity;
    private float discountRate;

}
