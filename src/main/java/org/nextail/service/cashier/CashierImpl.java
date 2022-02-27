package org.nextail.service.cashier;

import org.nextail.domain.discount.Discount;
import org.nextail.domain.product.Product;

import java.util.List;

public class CashierImpl implements Cashier {

    private List<Discount> discountList;

    public CashierImpl(List<Discount> discountList) {
        this.discountList = discountList;
    }

    @Override
    public Float addProduct(Product product, String cartId) {


        return getPrice(cartId);
    }

    @Override
    public Float getPrice(String cartId) {

        return null;
    }
}
