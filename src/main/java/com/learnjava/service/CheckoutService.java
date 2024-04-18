package com.learnjava.service;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

import java.util.List;
import java.util.stream.Collectors;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;

public class CheckoutService {
	private PriceValidatorService priceValidatorService;

	public CheckoutService(PriceValidatorService priceValidatorService){
		this.priceValidatorService = priceValidatorService;
	}

	public CheckoutResponse checkout(Cart cart){
		startTimer();
		List<CartItem> priceValidationList = cart.getCartItemList()
				.stream()
				.map(cartItem -> {
					boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(cartItem);
					cartItem.setExpired(isPriceInvalid);
					return cartItem;
				})
				.filter(CartItem::isExpired)
				.collect(Collectors.toList());

		if(priceValidationList.size()>0){
			return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
		}

		timeTaken();
//		double finalPrice = calculateFinalPrice(cart);
		double finalPrice = calculateFinalPrice_reduce(cart);
		log(" Checkout Complete and the final price is " + finalPrice);
		return new CheckoutResponse(CheckoutStatus.SUCCESS);
	}

	private double calculateFinalPrice(Cart cart) {

		return cart.getCartItemList()
				.parallelStream()
				.map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
				.mapToDouble(Double::doubleValue)
				.sum();
	}

	private double calculateFinalPrice_reduce(Cart cart) {

		return cart.getCartItemList()
				.parallelStream()
				.map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
				.reduce(0.0, Double::sum);
	}
}
