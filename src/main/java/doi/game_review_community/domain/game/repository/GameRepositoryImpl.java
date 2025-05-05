package doi.game_review_community.domain.game.repository;

import doi.game_review_community.domain.game.Game;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class GameRepositoryImpl implements GameRepository {
    //임시 저장소
    private static final Map<Long, Game> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Optional<Game> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Game> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void save(Game game) {
        game.setId(sequence.incrementAndGet());
        store.put(game.getId(), game);
    }

    @Override
    public void update(Long id, Game updateParam) {
        Game existing = store.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("존재하지 않는 게임입니다. id=" + id);
        }
        //필드교체
        existing.setName(updateParam.getName());
        existing.setPlatforms(updateParam.getPlatforms());
        existing.setReleaseDate(updateParam.getReleaseDate());
        existing.setDeveloper(updateParam.getDeveloper());
        existing.setPublisher(updateParam.getPublisher());
        existing.setDescription(updateParam.getDescription());
        existing.setImageUrl(updateParam.getImageUrl());
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public List<Game> findByNameContainingIgnoreCase(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return findAll();
        }
        String lower = keyword.toLowerCase();
        return store.values().stream()
                .filter(game -> game.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }
}
