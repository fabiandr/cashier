package org.nextail.service.cart;

import org.nextail.domain.product.Product;

import java.util.Map;

public interface CartManager {

    void addProductToCart(Product product, String cartId);

    Map<Product, Integer> getCartContent(String cartId);

}
