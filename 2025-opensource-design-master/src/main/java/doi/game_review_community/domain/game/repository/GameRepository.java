package doi.game_review_community.domain.game.repository;

import doi.game_review_community.domain.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    // 이름으로 검색 (대소문자 무시, 포함 검색)
    List<Game> findByNameContainingIgnoreCase(String keyword);

    // 게임이 이미 존재하는지 여부를 검사(rawg api에서 불러올지를 결정)
    boolean existsByName(String name);
}
