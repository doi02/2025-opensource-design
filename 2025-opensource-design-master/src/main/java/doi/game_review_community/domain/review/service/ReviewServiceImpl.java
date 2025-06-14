package doi.game_review_community.domain.review.service;

import doi.game_review_community.domain.game.service.GameService;
import doi.game_review_community.domain.review.Review;
import doi.game_review_community.domain.review.repository.ReviewRepository;
import doi.game_review_community.domain.user.service.UserService;
import doi.game_review_community.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final GameService gameService;
    private final UserService userService;

    @Override
    public Review findByReviewId(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id=" + id));
    }

    @Override
    public List<Review> findByGameId(Long gameId) {
        return reviewRepository.findByGameId(gameId);
    }

    @Override
    @Transactional
    public Long addReview(Long gameId, Long userId, ReviewDto dto) {
        // 중복 작성 검사
        reviewRepository.findByGameIdAndAuthorId(gameId, userId)
                .ifPresent(r -> {
                    throw new IllegalStateException("이미 이 게임에 대한 리뷰를 작성하였습니다.");
                });

        var game = gameService.findGameById(gameId);
        var user = userService.findUserById(userId);

        Review review = new Review(
                game,
                user,
                dto.getContent(),
                dto.getGraphicScore(),
                dto.getStoryScore(),
                dto.getGameplayScore(),
                dto.getSoundScore()
        );
        Review saved = reviewRepository.save(review);
        return saved.getId();
    }

    @Override
    @Transactional
    public void updateReview(Long reviewId, ReviewDto dto) {
        Review existing = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id=" + reviewId));

        existing.setContent(dto.getContent());
        existing.setGraphicScore(dto.getGraphicScore());
        existing.setStoryScore(dto.getStoryScore());
        existing.setGameplayScore(dto.getGameplayScore());
        existing.setSoundScore(dto.getSoundScore());

        // 기존 로직 그대로: soundScore가 두 번 더해진 sum
        double sum = dto.getGameplayScore()
                + dto.getStoryScore()
                + dto.getSoundScore()
                + dto.getSoundScore();
        existing.setTotalScore(sum * 5);
        // Dirty Checking으로 자동 반영
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    @Transactional
    public void toggleLikeReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id=" + reviewId));
        review.toggleLike(userId);
    }

    @Override
    @Transactional
    public void toggleDislikeReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id=" + reviewId));
        review.toggleDislike(userId);
    }
}
