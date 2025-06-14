package doi.game_review_community.init;

import doi.game_review_community.domain.game.Game;
import doi.game_review_community.domain.game.Platform;
import doi.game_review_community.domain.game.service.GameService;
import doi.game_review_community.domain.user.service.UserService;
import doi.game_review_community.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

//개발용 계정 자동생성
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final GameService gameService;

    @Override
    public void run(String... args) throws Exception {
        String devUsername = "devuser", devEmail = "dev@local", devPass = "ddev1234";
        if (userService.isUsernameAvailable(devUsername)) {
            UserRegistrationDto dto = new UserRegistrationDto();
            dto.setUsername(devUsername);
            dto.setEmail(devEmail);
            dto.setPassword(devPass);
            dto.setConfirmPassword(devPass);
            userService.join(dto);
        }

        if (!gameService.findAllGames().isEmpty()) {
            return;
        }

        // 샘플 게임 3개 등록
        gameService.addGame(Game.builder()
                .name("Red Dead Redemption 2")
                .platforms(List.of(
                        Platform.PC,
                        Platform.PLAYSTATION_4,
                        Platform.XBOX_ONE
                ))
                .releaseDate(LocalDate.of(2018, 10, 26)) // 콘솔 기준
                .developer("Rockstar Games")
                .publisher("Rockstar Games")
                .description("An epic tale of life in America’s unforgiving heartland. Experience the story of outlaw Arthur Morgan and the Van der Linde gang.")
                .imageUrl("https://upload.wikimedia.org/wikipedia/en/4/44/Red_Dead_Redemption_II.jpg")
                .build()
        );

        gameService.addGame(Game.builder()
                .name("Elden Ring")
                .platforms(List.of(
                        Platform.PC,
                        Platform.PLAYSTATION_4,
                        Platform.PLAYSTATION_5,
                        Platform.XBOX_ONE,
                        Platform.XBOX_SERIES_X
                ))
                .releaseDate(LocalDate.of(2022, 2, 25))
                .developer("FromSoftware")
                .publisher("Bandai Namco Entertainment")
                .description("A vast world where open fields with a variety of situations and huge dungeons with complex and three-dimensional designs are seamlessly connected.")
                .imageUrl("https://upload.wikimedia.org/wikipedia/en/b/b9/Elden_Ring_Box_art.jpg")
                .build()
        );

        gameService.addGame(Game.builder()
                .name("The Legend of Zelda: Breath of the Wild")
                .platforms(List.of(Platform.NINTENDO_SWITCH, Platform.NINTENDO_WII_U))
                .releaseDate(LocalDate.of(2017, 3, 3))
                .developer("Nintendo EPD")
                .publisher("Nintendo")
                .description("Step into a world of discovery, exploration and adventure in The Legend of Zelda: Breath of the Wild, a boundary-breaking game in the acclaimed series.")
                .imageUrl("https://ko.wikipedia.org/wiki/%EC%A0%A4%EB%8B%A4%EC%9D%98_%EC%A0%84%EC%84%A4_%EB%B8%8C%EB%A0%88%EC%8A%A4_%EC%98%A4%EB%B8%8C_%EB%8D%94_%EC%99%80%EC%9D%BC%EB%93%9C#/media/%ED%8C%8C%EC%9D%BC:%EB%B8%8C%EB%A0%88%EC%8A%A4_%EC%98%A4%EB%B8%8C_%EB%8D%94_%EC%99%80%EC%9D%BC%EB%93%9C.jpg")
                .build()
        );

    }
}
