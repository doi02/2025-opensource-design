package doi.game_review_community.domain.review.service;

import doi.game_review_community.domain.game.service.GameService;
import doi.game_review_community.domain.review.Review;
import doi.game_review_community.domain.review.repository.ReviewRepository;
import doi.game_review_community.domain.user.service.UserService;
import doi.game_review_community.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final GameService gameService;
    private final UserService userService;


    @Override
    public Review findByReviewId(Long id){
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id=" + id));
    }

    @Override
    public List<Review> findByGameId(Long gameId) {
        return reviewRepository.findByGameId(gameId);
    }

    @Override
    public Long addReview(Long gameId, Long userId, ReviewDto dto) {
        reviewRepository.findByGameIdAndAuthorId(gameId, userId)
                .ifPresent(r-> {
                    throw new IllegalStateException("이미 이 게임에 대한 리뷰를 작성하였습니다.");
                });
        var game = gameService.findGameById(gameId);
        var user = userService.findUserById(userId);
        Review review = new Review(game, user,
                dto.getContent(),
                dto.getGraphicScore(),
                dto.getStoryScore(),
                dto.getGameplayScore(),
                dto.getSoundScore()
        );
        reviewRepository.save(review);
        return review.getId();
    }

    @Override
    public void updateReview(Long reviewId, ReviewDto dto) {
        Review existing = reviewRepository.findById(reviewId)
                        .orElseThrow(()-> new IllegalArgumentException("Review not found id=" + reviewId));
        existing.setContent(dto.getContent());
        existing.setGraphicScore(dto.getGraphicScore());
        existing.setStoryScore(dto.getStoryScore());
        existing.setGameplayScore(dto.getGameplayScore());
        existing.setSoundScore(dto.getSoundScore());

        double sum = dto.getGameplayScore()
                + dto.getStoryScore()
                + dto.getSoundScore()
                + dto.getSoundScore();
        existing.setTotalScore(sum * 5);
        //리포지토리 이미 반영됨
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.delete(reviewId);
    }

    @Override
    public void toggleLikeReview(Long reviewId, Long userId) {
        reviewRepository.toggleLike(reviewId, userId);
    }

    @Override
    public void toggleDislikeReview(Long reviewId, Long userId) {
        reviewRepository.toggleDislike(reviewId, userId);
    }
}
