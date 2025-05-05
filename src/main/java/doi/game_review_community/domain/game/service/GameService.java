package doi.game_review_community.domain.game.service;

import doi.game_review_community.domain.game.Game;
import java.util.List;

public interface GameService {

    List<Game> findAllGames();

    Game findGameById(Long id);

    Long addGame(Game game);

    void updateGame(Long id, Game updateParam);

    void deleteGame(Long id);

    List<Game> searchByName(String searchText);
}