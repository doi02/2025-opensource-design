package doi.game_review_community.rawg.service;

import doi.game_review_community.domain.game.Game;
import doi.game_review_community.domain.game.Platform;
import doi.game_review_community.domain.game.repository.GameRepository;
import doi.game_review_community.rawg.dto.Platform.PlatformWrapperDto;
import doi.game_review_community.rawg.dto.RawgGameDto;
import doi.game_review_community.rawg.dto.RawgGamesResponseDto;
import doi.game_review_community.rawg.dto.developer.DeveloperWrapperDto;
import doi.game_review_community.rawg.dto.publisher.PublisherWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RawgGameServiceImpl implements RawgGameService {

    private final RestTemplate rawgRestTemplate;
    private final GameRepository gameRepository;

    // api 키 넣기
    @Value("${rawg.api.key}")
    private String apiKey;

    @Override
    public List<Game> importAndSearch(String keyword) {
        String url = String.format(
                "/games?key=%s&search=%s&ordering=-metacritic&page_size=2",
                apiKey, keyword
        );
        RawgGamesResponseDto response = rawgRestTemplate.getForObject(url, RawgGamesResponseDto.class);
        if (response == null || response.getResults() == null) {
            return List.of();
        }

        return response.getResults().stream()
                .map(this::toGameEntity)
                // DB에 같은 이름의 게임이 없을 때만 save()
                .filter(game -> !gameRepository.existsByName(game.getName()))
                .map(gameRepository::save)
                .collect(Collectors.toList());
    }

    // rawg api를 game엔티티로 변환
    private Game toGameEntity(RawgGameDto dto) {
        Game game = new Game();

        // 기본 정보 설정
        game.setName(dto.getName());
        game.setReleaseDate(dto.getReleaseDate());
        game.setImageUrl(dto.getImageUrl());
        game.setDescription(dto.getDescription());

        // 개발사 목록을 문자열로 연결
        if (dto.getDevelopers() != null) {
            String devCombined = dto.getDevelopers().stream()
                    .map(DeveloperWrapperDto::getDeveloper)
                    .map(dev -> dev.getName())
                    .collect(Collectors.joining(", "));
            game.setDeveloper(devCombined);
        }

        // 배급사 목록을 문자열로 연결
        if (dto.getPublishers() != null) {
            String pubCombined = dto.getPublishers().stream()
                    .map(PublisherWrapperDto::getPublisher)
                    .map(pub -> pub.getName())
                    .collect(Collectors.joining(", "));
            game.setPublisher(pubCombined);
        }

        // 플랫폼 목록을 enum으로 변환
        if (dto.getPlatforms() != null) {
            List<Platform> platformList = dto.getPlatforms().stream()
                    .map(PlatformWrapperDto::getPlatform)
                    .map(pDetail -> pDetail.getName().toUpperCase().replace(" ", "_"))
                    .filter(name -> {
                        try {
                            Platform.valueOf(name);
                            return true;
                        } catch (IllegalArgumentException ex) {
                            // 매핑 불가능한 플랫폼명은 무시
                            return false;
                        }
                    })
                    .map(Platform::valueOf)
                    .collect(Collectors.toList());
            game.setPlatforms(platformList);
        }

        return game;
    }
}
