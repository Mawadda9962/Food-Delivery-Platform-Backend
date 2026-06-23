package com.example.demo_TRA.DTOs.SummeryDTO;

import com.example.demo_TRA.Entities.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSummeryDTO {
    private Long id;
    private Integer rating;
    private String comment;

    public static ReviewSummeryDTO fromEntity(Review review) {

        ReviewSummeryDTO dto = new ReviewSummeryDTO();

        dto.setRating(review.getRating());
        dto.setComment(review.getComment());

        return dto;
    }
}
