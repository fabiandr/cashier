package org.nextail.service.cart;

import org.apache.commons.lang3.StringUtils;
import org.nextail.domain.product.Product;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryCartManagerImpl implements CartManager {

    Map<String, Map<Product, Integer>> carts;

    public InMemoryCartManagerImpl() {
        carts = new HashMap<>(0);
    }

    @Override
    public void addProductToCart(Product product, String cartId) {

        if (product == null || StringUtils.isBlank(cartId)) throw new InvalidParameterException("Product and cartId are mandatory");

        Map<Product, Integer> cart = getCartContentByCartId(cartId);
        Integer currentQty = cart.get(product) != null ? cart.get(product) : 0;
        cart.put(product, currentQty + 1 );
        carts.put(cartId, cart);
    }

    @Override
    public Map<Product, Integer> getCartContent(String cartId) {

        if (StringUtils.isBlank(cartId)) throw new InvalidParameterException("CartId is mandatory");

        return getCartContentByCartId(cartId);
    }

    /**
     * Internal method without validations for internal access
     * Prevent to perform same validation multiple times
     * @param cartId
     * @return
     */
    private Map<Product, Integer> getCartContentByCartId(String cartId) {
        return carts.get(cartId) != null ? carts.get(cartId) : new HashMap<>();
    }
}
