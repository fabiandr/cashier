package org.nextail.domain.product;

public class ProductGenerator {

    public static Product getVoucher() {
        return new Product("VOUCHER", "Gift Card", 5f);
    }

    public static Product getTshirt() {
        return new Product("TSHIRT", "Summer T-Shirt", 20f);
    }

    public static Product getPants() {
        return new Product("PANTS", "Summer Pants", 7.5f);
    }
}
