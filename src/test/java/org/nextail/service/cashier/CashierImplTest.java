package org.nextail.service.cashier;

import org.junit.Before;
import org.junit.Test;
import org.nextail.domain.discount.Discount;
import org.nextail.domain.product.Product;
import org.nextail.domain.product.ProductGenerator;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CashierImplTest {

    private Cashier cashier;

    @Before
    public void setUp() {
        Discount twoForOneVoucher = new Discount("VOUCHER", 2, 2, 50F);
        Discount fivePercentDiscount = new Discount("TSHIRT", 3, 0, 5F);

        cashier = new CashierImpl(new HashSet<>(Arrays.asList(twoForOneVoucher, fivePercentDiscount)));
    }

    @Test(expected = InvalidParameterException.class)
    public void cartId_must_not_be_null_when_adding_new_product() {
        cashier.scan(ProductGenerator.getVoucher(), null);
    }

    @Test(expected = InvalidParameterException.class)
    public void cartId_must_not_be_empty_when_adding_new_product() {
        cashier.scan(ProductGenerator.getVoucher(), "");
    }

    @Test(expected = InvalidParameterException.class)
    public void product_must_not_be_null_when_adding_new_product() {
        cashier.scan(null, generateCartId());
    }

    @Test
    public void cart_price_should_be_product_price_when_only_one_product_is_added(){
        Product pants = ProductGenerator.getPants();

        Float cartPrice = cashier.scan(pants, generateCartId());

        assertEquals(Float.valueOf(7.5F), cartPrice);
    }

    @Test
    public void cart_price_should_be_n_product_price_when_only_product_is_added_n_times_and_no_discount_is_applied(){
        Product pants = ProductGenerator.getPants();

        String cartId = generateCartId();
        cashier.scan(pants, cartId);
        cashier.scan(pants, cartId);
        cashier.scan(pants, cartId);
        cashier.scan(pants, cartId);
        Float cartPrice = cashier.scan(pants, cartId);

        assertEquals(Float.valueOf(37.5F), cartPrice);
    }

    @Test
    public void should_not_apply_discount_when_min_required_quantity_is_not_reached() {
        Product tshirt = ProductGenerator.getTshirt();

        String cartId = generateCartId();
        cashier.scan(tshirt, cartId);
        Float cartPrice = cashier.scan(tshirt, cartId);

        assertEquals(Float.valueOf(40F), cartPrice);
    }

    @Test
    public void should_apply_discount_when_min_required_quantity_is_reached() {
        Product tshirt = ProductGenerator.getTshirt();

        String cartId = generateCartId();
        cashier.scan(tshirt, cartId);
        cashier.scan(tshirt, cartId);
        Float cartPrice = cashier.scan(tshirt, cartId);

        assertEquals(Float.valueOf(57F), cartPrice);
    }

    @Test
    public void should_apply_discount_to_all_units_when_min_required_quantity_is_reached_and_max_amount_is_not_set() {
        Product tshirt = ProductGenerator.getTshirt();

        String cartId = generateCartId();
        cashier.scan(tshirt, cartId);
        cashier.scan(tshirt, cartId);
        cashier.scan(tshirt, cartId);
        cashier.scan(tshirt, cartId);
        cashier.scan(tshirt, cartId);
        Float cartPrice = cashier.scan(tshirt, cartId);

        assertEquals(Float.valueOf(114F), cartPrice);
    }

    @Test
    public void should_apply_discount_to_n_times_max_established_quantity_and_not_to_remainder_unit() {
        Product voucher = ProductGenerator.getVoucher();

        String cartId = generateCartId();
        cashier.scan(voucher, cartId);
        cashier.scan(voucher, cartId);
        Float cartPrice = cashier.scan(voucher, cartId);

        assertEquals(Float.valueOf(10F), cartPrice);
    }

    @Test
    public void should_not_apply_discounts_when_different_products_are_added_and_min_required_quantity_is_not_reached() {
        String cartId = generateCartId();
        cashier.scan(ProductGenerator.getVoucher(), cartId);
        cashier.scan(ProductGenerator.getTshirt(), cartId);
        cashier.scan(ProductGenerator.getTshirt(), cartId);
        Float cartPrice = cashier.scan(ProductGenerator.getPants(), cartId);

        assertEquals(Float.valueOf(52.5F), cartPrice);
    }

    @Test
    public void should_not_apply_discounts_when_different_products_are_added_and_min_required_quantity_is_reached() {
        String cartId = generateCartId();
        cashier.scan(ProductGenerator.getVoucher(), cartId);
        cashier.scan(ProductGenerator.getVoucher(), cartId);
        cashier.scan(ProductGenerator.getTshirt(), cartId);
        cashier.scan(ProductGenerator.getTshirt(), cartId);
        cashier.scan(ProductGenerator.getTshirt(), cartId);
        Float cartPrice = cashier.scan(ProductGenerator.getPants(), cartId);

        assertEquals(Float.valueOf(69.5F), cartPrice);
    }

    private String generateCartId() {
        return UUID.randomUUID().toString();
    }
}