package org.nextail.service.cart;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nextail.domain.product.Product;
import org.nextail.domain.product.ProductGenerator;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.UUID;

public class InMemoryCartManagerImplTest {

    private CartManager cartManager;

    @Before
    public void setUp() {
        cartManager = new InMemoryCartManagerImpl();
    }

    @Test(expected = InvalidParameterException.class)
    public void cartId_must_not_be_null_when_getting_cart_content() {
        cartManager.getCartContent(null);
    }

    @Test(expected = InvalidParameterException.class)
    public void cartId_must_not_be_empty_when_getting_cart_content() {
        cartManager.getCartContent("");
    }

    @Test
    public void should_return_empty_cart_when_no_products_were_added() {
        Map<Product, Integer> cartContent = cartManager.getCartContent(generateCartId());
        Assert.assertTrue(cartContent.isEmpty());
    }

    @Test
    public void should_return_cart_with_content_when_products_where_added_to_cart() {
        String currentCartId = generateCartId();

        cartManager.addProductToCart(ProductGenerator.getVoucher(), currentCartId);

        Map<Product, Integer> cartContent = cartManager.getCartContent(currentCartId);
        Assert.assertFalse(cartContent.isEmpty());
    }

    @Test(expected = InvalidParameterException.class)
    public void product_must_not_be_null_when_trying_to_add_product() {
        cartManager.addProductToCart(null, generateCartId());
    }

    @Test(expected = InvalidParameterException.class)
    public void cartId_must_not_be_null_when_trying_to_add_product() {
        cartManager.addProductToCart(ProductGenerator.getVoucher(), null);
    }

    @Test(expected = InvalidParameterException.class)
    public void cartId_must_not_be_empty_when_trying_to_add_product() {
        cartManager.addProductToCart(ProductGenerator.getVoucher(), "");
    }

    @Test
    public void should_have_quantity_of_one_when_product_is_added_first_time() {
        String currentCartId = UUID.randomUUID().toString();
        Product voucher = ProductGenerator.getVoucher();
        cartManager.addProductToCart(voucher, currentCartId);

        Map<Product, Integer> cartContent = cartManager.getCartContent(currentCartId);
        Assert.assertEquals(Integer.valueOf(1), cartContent.get(voucher));
    }

    @Test
    public void should_increase_quantity_to_two_for_product_if_same_product_is_added_two_times() {
        String currentCartId = UUID.randomUUID().toString();
        Product voucher = ProductGenerator.getVoucher();
        cartManager.addProductToCart(voucher, currentCartId);
        cartManager.addProductToCart(voucher, currentCartId);

        Map<Product, Integer> cartContent = cartManager.getCartContent(currentCartId);
        Assert.assertEquals(Integer.valueOf(2), cartContent.get(voucher));
    }

    @Test
    public void should_return_cart_with_two_products_when_two_different_products_are_added() {
        String currentCartId = UUID.randomUUID().toString();

        cartManager.addProductToCart(ProductGenerator.getVoucher(), currentCartId);
        cartManager.addProductToCart(ProductGenerator.getTshirt(), currentCartId);

        Map<Product, Integer> cartContent = cartManager.getCartContent(currentCartId);
        Assert.assertEquals(2, cartContent.size());
    }

    @Test
    public void should_return_correct_cart_products_for_each_cart_when_two_different_carts_are_created_at_same_time() {
        String firstCartId = UUID.randomUUID().toString();
        String secondCartId = UUID.randomUUID().toString();

        cartManager.addProductToCart(ProductGenerator.getVoucher(), firstCartId);
        cartManager.addProductToCart(ProductGenerator.getTshirt(), secondCartId);

        Map<Product, Integer> firstCartContent = cartManager.getCartContent(firstCartId);
        Map<Product, Integer> secondCartContent = cartManager.getCartContent(secondCartId);

        Assert.assertEquals(ProductGenerator.getVoucher(), firstCartContent.entrySet().stream().findFirst().get().getKey());
        Assert.assertEquals(ProductGenerator.getTshirt(), secondCartContent.entrySet().stream().findFirst().get().getKey());
    }

    private String generateCartId() {
        return UUID.randomUUID().toString();
    }

}