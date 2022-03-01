package org.nextail.service.cashier;

import org.nextail.domain.product.Product;

public interface Cashier {

    Float addProduct(Product product, String cartId);

}
