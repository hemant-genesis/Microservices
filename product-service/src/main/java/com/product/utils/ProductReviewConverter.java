package com.product.utils;

import com.product.dto.requests.ProductReviewRequest;
import com.product.dto.responses.ProductReviewResponse;
import com.product.entity.ProductReview;

public class ProductReviewConverter {
    // Convert ProductReviewRequest to ProductReview
    public static ProductReview toProductReview(ProductReviewRequest productReviewRequest) {
        ProductReview productReview = new ProductReview();
        productReview.setReview(productReviewRequest.getReview());
        productReview.setRating(productReviewRequest.getRating());
        // ID is not set here because ProductReviewRequest doesn't have an ID
        return productReview;
    }

    // Convert ProductReview to ProductReviewResponse
    public static ProductReviewResponse toProductReviewResponse(ProductReview productReview) {
        ProductReviewResponse productReviewResponse = new ProductReviewResponse();
        productReviewResponse.setId(productReview.getId());
        productReviewResponse.setReview(productReview.getReview());
        productReviewResponse.setRating(productReview.getRating());
        return productReviewResponse;
    }
}
