package org.nextail.domain.discount;

import lombok.Data;

@Data
public class Discount {

    private String productCode;
    private int requiredQuantity;
    private int maxQuantity;
    private float discountRate;

}
