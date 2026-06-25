package com.example.demo.TRA.DTOs.RequestDTO;

import com.example.demo.TRA.Entities.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewRequestDTO {

    @NotBlank(message = "Target type is required (e.g., RESTAURANT or DRIVER)")
    private String targetType;

    @NotNull(message = "Rating score is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private Integer rating;

    private String comment;
    private LocalDateTime createdAt;

    @NotNull(message = "Customer ID is required")
    private Long customerId;


    public Review toEntity() { // For Creating
        Review review = new Review();

        review.setTargetType(targetType);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(createdAt);

        return review;
    }

    public void applyTo(Review review) { // For Updating
        review.setTargetType(targetType);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(createdAt);
    }

}
