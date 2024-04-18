package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.learnjava.domain.Inventory;
import com.learnjava.domain.Product;
import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.ProductOption;
import com.learnjava.domain.Review;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService,
            InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }



    public Product retrieveProductDetails(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(
                () -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(
                () -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture.thenCombine(
                reviewCompletableFuture, (p, r) -> new Product(productId, p, r))
                .join();
        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product; //block the thread
    }

    /**
     * this how code in server side. note that no blocking code. it's responsibility of the client
     * to do required blocking operations
     * @param productId
     * @return
     */
    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(
                () -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(
                () -> reviewService.retrieveReviews(productId));

        return productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (p, r) -> new Product(productId, p, r));
    }

    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventory(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(
                () -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture.thenCombine(
                        reviewCompletableFuture, (p, r) -> new Product(productId, p, r))
                .join();
        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product; //block the thread
    }

    public Product retrieveProductDetailsWithInventory_approach2(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventory_approach2(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId))
                .exceptionally(e->{
                    log("Handled the Exception in reviewService: " + e.getMessage());
                    return Review.builder()
                            .noOfReviews(0)
                            .overallRating(0.0)
                            .build();
                });

        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (p, r) -> new Product(productId, p, r))
                .whenComplete((product1, ex)->{
                    log("Inside WhenComplete : "+ product1 + " and the exception is " + ex);
                })
                .join();//block the thread
        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return product; //block the thread
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
        List<ProductOption> productOptions = productInfo.getProductOptions().stream().map(productOption -> {
            Inventory inventory = inventoryService.retrieveInventory(productOption);
            productOption.setInventory(inventory);
            return productOption;
        }).collect(Collectors.toList());

        return productOptions;
    }

    private List<ProductOption> updateInventory_approach2(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> productOptions = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    return CompletableFuture.supplyAsync(()->inventoryService.retrieveInventory(productOption))
                            .thenApply(inventory -> {
                                productOption.setInventory(inventory);
                                return productOption;
                            });
        })
                .collect(Collectors.toList());

        return productOptions.stream().map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
