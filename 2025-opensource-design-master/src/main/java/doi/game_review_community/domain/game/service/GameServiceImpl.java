package doi.game_review_community.domain.game.service;

import doi.game_review_community.domain.game.Game;
import doi.game_review_community.domain.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    @Transactional
    public Long addGame(Game game) {
        Game saved = gameRepository.save(game);
        return saved.getId();
    }

    @Override
    @Transactional
    public void updateGame(Long id, Game updateParam) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게임이 없습니다. id=" + id));

        game.setName(updateParam.getName());
        game.setPlatforms(updateParam.getPlatforms());
        game.setReleaseDate(updateParam.getReleaseDate());
        game.setDeveloper(updateParam.getDeveloper());
        game.setPublisher(updateParam.getPublisher());
        game.setDescription(updateParam.getDescription());
        game.setImageUrl(updateParam.getImageUrl());
    }

    @Override
    @Transactional
    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new IllegalArgumentException("게임이 없습니다. id=" + id);
        }
        gameRepository.deleteById(id);
    }

    @Override
    public List<Game> searchByName(String searchText) {
        return gameRepository.findByNameContainingIgnoreCase(searchText);
    }
}
