package org.nextail.service.cashier;

import org.nextail.domain.product.Product;

public class CashierImpl implements Cashier {

    @Override
    public Float addProduct(Product product, String cartId) {


        return getPrice(cartId);
    }

    @Override
    public Float getPrice(String cartId) {

        return null;
    }
}
