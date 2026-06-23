package com.example.demo_TRA.DTOs.ResponseDTO;

import com.example.demo_TRA.Entities.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {

    private String targetType;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public static ReviewResponseDTO fromEntity(Review review) {
        if (review == null) {
            return null;
        }

        ReviewResponseDTO dto = new ReviewResponseDTO();

        dto.setTargetType(review.getTargetType());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());

        return dto;
    }

    public static List<ReviewResponseDTO> fromEntity(List<Review> reviews) {
        List<ReviewResponseDTO> dtos = new ArrayList<>();
        if (reviews != null) {
            for (Review review : reviews) {
                dtos.add(fromEntity(review));
            }
        }
        return dtos;
    }

}
