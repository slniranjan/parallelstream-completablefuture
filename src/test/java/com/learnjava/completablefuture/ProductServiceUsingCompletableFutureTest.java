package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ProductService;
import com.learnjava.service.ReviewService;

class ProductServiceUsingCompletableFutureTest {

	private ProductInfoService productInfoService = new ProductInfoService();
	private ReviewService reviewService = new ReviewService();
	private InventoryService is = new InventoryService();

	ProductServiceUsingCompletableFuture pscf = new ProductServiceUsingCompletableFuture(productInfoService,reviewService, is);

	@Test
	void retrieveProductDetails() {
		//given
		String productId = "ABC123";

		//when
		Product product = pscf.retrieveProductDetails(productId);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		assertNotNull(product.getReview());

	}

	@Test
	void retrieveProductDetails_approach2() {
		//given
		String productId = "ABC123";

		//when
		CompletableFuture<Product> productCompletableFuture = pscf.retrieveProductDetails_approach2(productId);
		Product product = productCompletableFuture.join();
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		assertNotNull(product.getReview());

	}

	@Test
	void retrieveProductDetailsWithInventory() {
		//given
		String productId = "ABC123";

		//when
		Product product = pscf.retrieveProductDetailsWithInventory(productId);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		product.getProductInfo().getProductOptions()
				.forEach(productOption -> {
					assertNotNull(productOption.getInventory());
				});
		assertNotNull(product.getReview());
	}

	@Test
	void retrieveProductDetailsWithInventory_approach2() {
		//given
		String productId = "ABC123";

		//when
		Product product = pscf.retrieveProductDetailsWithInventory_approach2(productId);
		assertTrue(product.getProductInfo().getProductOptions().size() > 0);
		product.getProductInfo().getProductOptions()
				.forEach(productOption -> {
					assertNotNull(productOption.getInventory());
				});
		assertNotNull(product.getReview());
	}
}
