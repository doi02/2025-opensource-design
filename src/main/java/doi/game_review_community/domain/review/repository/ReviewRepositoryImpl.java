package doi.game_review_community.domain.review.repository;

import doi.game_review_community.domain.review.Review;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    //임시 저장소
    private static final Map<Long, Review> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Optional<Review> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Review> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Review> findByGameId(Long gameId) {
        return store.values().stream()
                .filter(r -> r.getGame().getId().equals(gameId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Review> findByGameIdAndAuthorId(Long gameId, Long authorId) {
        return store.values().stream()
                .filter(review ->
                        review.getGame().getId().equals(gameId) &&
                                review.getAuthor().getId().equals(authorId)
                )
                .findAny();
    }

    @Override
    public void save(Review review) {
        review.setId(sequence.incrementAndGet());
        store.put(review.getId(), review);
    }

    @Override
    public void update(Long id, Review updateParam) {
        Review existing = store.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("존재하지 않는 리뷰입니다. id=" + id);
        }
        if (updateParam.getContent() != null) existing.setContent(updateParam.getContent());
        existing.setGraphicScore(updateParam.getGraphicScore());
        existing.setStoryScore(updateParam.getStoryScore());
        existing.setGameplayScore(updateParam.getGameplayScore());
        existing.setSoundScore(updateParam.getSoundScore());
        // totalScore, modifiedDate 자동 계산 @PreUpdate
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public void toggleLike(Long id, Long userId) {
        Review existing = store.get(id);
        if (existing == null) return;
        existing.toggleLike(userId);
    }

    @Override
    public void toggleDislike(Long id, Long userId) {
        Review existing = store.get(id);
        if (existing == null) return;
        existing.toggleDislike(userId);
    }
}
