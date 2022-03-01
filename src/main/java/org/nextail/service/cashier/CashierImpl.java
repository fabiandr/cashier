package org.nextail.service.cashier;

import org.apache.commons.lang3.StringUtils;
import org.nextail.domain.discount.Discount;
import org.nextail.domain.product.Product;
import org.nextail.service.cart.CartManager;
import org.nextail.service.cart.InMemoryCartManagerImpl;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class CashierImpl implements Cashier {

    private Set<Discount> discountList;
    private CartManager cartManager;

    public CashierImpl(Set<Discount> discountList) {
        this.discountList = discountList;
        this.cartManager = new InMemoryCartManagerImpl();
    }

    @Override
    public Float addProduct(Product product, String cartId) {
        if (product == null || StringUtils.isBlank(cartId))
            throw new InvalidParameterException("Required parameters not present");

        cartManager.addProductToCart(product, cartId);

        return getCartPrice(cartId);
    }

    private Float getCartPrice(String cartId) {
        Map<Product, Integer> cartContent = cartManager.getCartContent(cartId);
        Float price = 0F;
        for (Map.Entry<Product, Integer> cartProduct : cartContent.entrySet()) {
            Product product = cartProduct.getKey();
            Integer quantity = cartProduct.getValue();
            price += getPriceForProduct(product, quantity);
        }
        return price;
    }

    private Float getPriceForProduct(Product product, Integer quantity) {
        Float price = 0F;
        Optional<Discount> discountForProduct = getDiscountForProduct(product);
        int fullPriceUnits = quantity;
        if (discountForProduct.isPresent() && quantity >= discountForProduct.get().getRequiredQuantity()) {
            if (discountForProduct.get().getMaxQuantity() > 0) {
                fullPriceUnits = quantity % discountForProduct.get().getMaxQuantity();
            } else {
                fullPriceUnits = 0;
            }
            price += (quantity - fullPriceUnits) * product.getPrice() * discountForProduct.get().getProductPriceMultiplier();
        }
        price += product.getPrice() * fullPriceUnits;
        return price;
    }

    private Optional<Discount> getDiscountForProduct(Product product) {
        for (Discount discount : discountList) {
            if (discount.getProductCode().equals(product.getCode())) {
                return Optional.of(discount);
            }
        }
        return Optional.empty();
    }
}
