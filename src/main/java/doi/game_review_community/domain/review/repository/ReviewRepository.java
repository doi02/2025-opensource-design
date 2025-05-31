package doi.game_review_community.domain.review.repository;

import doi.game_review_community.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 특정 게임에 달린 모든 리뷰 조회
    List<Review> findByGameId(Long gameId);

    // 같은 유저가 같은 게임에 리뷰를 하나만 작성하도록 검사
    Optional<Review> findByGameIdAndAuthorId(Long gameId, Long authorId);

}
