package doi.game_review_community.domain.review.repository;

import doi.game_review_community.domain.review.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    //reviewId로 리뷰찾기
    Optional<Review> findById(Long id);

    List<Review> findAll();

    //게임 Id로 해당 게임의 모든 리뷰 찾기
    List<Review> findByGameId(Long gameId);

    Optional<Review> findByGameIdAndAuthorId(Long gameId, Long authorId);

    void save(Review review);

    void update(Long id, Review updateParam);

    void delete(Long id);

    void toggleLike(Long id, Long userId);

    void toggleDislike(Long id, Long userId);

}
