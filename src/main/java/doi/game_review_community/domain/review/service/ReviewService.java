package doi.game_review_community.domain.review.service;

import doi.game_review_community.domain.review.Review;
import doi.game_review_community.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    Review findByReviewId(Long id);

    List<Review> findByGameId(Long gameId);

    Long addReview(Long gameId, Long userId, ReviewDto dto);

    void updateReview(Long reviewId, ReviewDto dto);

    void deleteReview(Long reviewId);

    void toggleLikeReview(Long reviewId, Long userId);

    void toggleDislikeReview(Long reviewId, Long userId);


}
