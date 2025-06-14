package doi.game_review_community.rawg.service;

import doi.game_review_community.domain.game.Game;
import java.util.List;

public interface RawgGameService {
    List<Game> importAndSearch(String keyword);
}
