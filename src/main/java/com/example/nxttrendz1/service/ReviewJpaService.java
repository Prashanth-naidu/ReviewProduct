package com.example.nxttrendz1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

import com.example.nxttrendz1.model.Product;
import com.example.nxttrendz1.model.Review;
import com.example.nxttrendz1.repository.ReviewRepository;
import com.example.nxttrendz1.repository.ReviewJpaRepository;
import com.example.nxttrendz1.repository.ProductJpaRepository;

@Service
public class ReviewJpaService implements ReviewRepository {

    @Autowired
    private ReviewJpaRepository db;

    @Autowired
    private ProductJpaRepository db1;

    @Override
    public ArrayList<Review> getReviews() {
        return (ArrayList<Review>) db.findAll();
    }

    @Override
    public Review getReviewById(int reviewId) {
        try {
            Review review = db.findById(reviewId).get();
            return review;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Review addReview(Review review) {
        Product product = review.getProduct();
        int productId = product.getProductId();
        try {
            product = db1.findById(productId).get();
            review.setProduct(product);
            db.save(review);

            return review;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Review updateReview(int reviewId, Review review) {
        try {
            Review newOne = db.findById(reviewId).get();
            if (review.getReviewContent() != null) {
                newOne.setReviewContent(review.getReviewContent());
            }
            if (review.getRating() != 0) {
                newOne.setRating(review.getRating());
            }
            if (review.getProduct() != null) {
                int productId = review.getProduct().getProductId();
                Product newProduct = db1.findById(productId).get();
                newOne.setProduct(newProduct);
            }

            db.save(newOne);

            return newOne;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteReview(int reviewId) {
        try {
            db.deleteById(reviewId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public Product getReviewProduct(int reviewId) {
        try {
            Review review = db.findById(reviewId).get();
            Product product = review.getProduct();
            return product;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
