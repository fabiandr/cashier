package org.nextail.service.cashier;

import junit.framework.TestCase;
import org.junit.Before;
import org.nextail.domain.discount.Discount;

import java.util.Arrays;

public class CashierImplTest extends TestCase {

    private Cashier cashier;

    @Before
    public void setUp() {
        Discount twoForOneVoucher = new Discount("VOUCHER", 2, 2, 50F);
        Discount fivePercentDiscount = new Discount("TSHIRT", 3, 0, 5F);

        cashier = new CashierImpl(Arrays.asList(twoForOneVoucher, fivePercentDiscount));
    }


}