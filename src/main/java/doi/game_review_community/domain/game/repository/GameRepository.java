package doi.game_review_community.domain.game.repository;

import doi.game_review_community.domain.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    // 이름으로 검색 (대소문자 무시, 포함 검색)
    List<Game> findByNameContainingIgnoreCase(String keyword);

}
