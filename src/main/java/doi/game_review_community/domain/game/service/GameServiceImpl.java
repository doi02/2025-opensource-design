package doi.game_review_community.domain.game.service;

import doi.game_review_community.domain.game.Game;
import doi.game_review_community.domain.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Override
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game findGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게임이 없습니다. id=" + id));
    }

    @Override
    public Long addGame(Game game) {
        gameRepository.save(game);
        return game.getId();
    }

    @Override
    public void updateGame(Long id, Game updateParam) {
        gameRepository.update(id, updateParam);
    }

    @Override
    public void deleteGame(Long id) {
        gameRepository.delete(id);
    }

    @Override
    public List<Game> searchByName(String keyword) {
        return gameRepository.findByNameContainingIgnoreCase(keyword);
    }
}