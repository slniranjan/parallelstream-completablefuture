package com.learnjava.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;

class CheckoutServiceTest {

	PriceValidatorService priceValidatorService = new PriceValidatorService();
	CheckoutService checkoutService = new CheckoutService(priceValidatorService);

	@Test
	void no_Of_cores() {
		//given

		//when
		System.out.println("no of cores :" + Runtime.getRuntime().availableProcessors());

		//then
	}

	@Test
	void checkout_6_items() {
		//given
		Cart cart = DataSet.createCart(6);

		//when
		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

		//then
		assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
		assertTrue(checkoutResponse.getFinalRate() > 0);
	}

	@Test
	void checkout_13_items() {
		//given
		Cart cart = DataSet.createCart(13);

		//when
		CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

		//then
		assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
	}

}
