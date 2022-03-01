package org.nextail.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    private String code;
    private String name;
    private Float price;

}
