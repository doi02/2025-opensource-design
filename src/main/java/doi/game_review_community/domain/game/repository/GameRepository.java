package doi.game_review_community.domain.game.repository;

import doi.game_review_community.domain.game.Game;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    Optional<Game> findById(Long id);

    List<Game> findAll();

    void save(Game game);

    void update(Long id, Game updateParam);

    void delete(Long id);

    List<Game> findByNameContainingIgnoreCase(String keyword);
}