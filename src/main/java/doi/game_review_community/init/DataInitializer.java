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
                .platforms(List.of(Platform.PC, Platform.PLAYSTATION_5, Platform.XBOX_ONE))
                .releaseDate(LocalDate.of(2018, 10, 26))
                .developer("Rockstar Games")
                .publisher("Rockstar Games")
                .description("An epic tale of life in America’s unforgiving heartland.")
                .imageUrl("https://example.com/rd2.jpg")
                .build()
        );

        gameService.addGame(Game.builder()
                .name("Elden Ring")
                .platforms(List.of(Platform.PC, Platform.PLAYSTATION_5, Platform.XBOX_SERIES_X))
                .releaseDate(LocalDate.of(2022, 2, 25))
                .developer("FromSoftware")
                .publisher("Bandai Namco")
                .description("A vast world full of perilous adventures and mighty foes.")
                .imageUrl("https://example.com/eldenring.jpg")
                .build()
        );

        gameService.addGame(Game.builder()
                .name("The Legend of Zelda: Breath of the Wild")
                .platforms(List.of(Platform.NINTENDO_SWITCH))
                .releaseDate(LocalDate.of(2017, 3, 3))
                .developer("Nintendo EPD")
                .publisher("Nintendo")
                .description("Explore the wilds of Hyrule any way you like.")
                .imageUrl("https://example.com/botw.jpg")
                .build()
        );
        gameService.addGame(Game.builder()
                .name("rrrr")
                .platforms(List.of(Platform.NINTENDO_SWITCH))
                .releaseDate(LocalDate.of(2017, 3, 3))
                .developer("Nintendo EPD")
                .publisher("Nintendo")
                .description("Explore the wilds of Hyrule any way you like.")
                .imageUrl("https://example.com/botw.jpg")
                .build()
        );
        gameService.addGame(Game.builder()
                .name("rrr2r")
                .platforms(List.of(Platform.NINTENDO_SWITCH))
                .releaseDate(LocalDate.of(2017, 3, 3))
                .developer("Nintendo EPD")
                .publisher("Nintendo")
                .description("Explore the wilds of Hyrule any way you like.")
                .imageUrl("https://example.com/botw.jpg")
                .build()
        );
        gameService.addGame(Game.builder()
                .name("rr3rr")
                .platforms(List.of(Platform.NINTENDO_SWITCH))
                .releaseDate(LocalDate.of(2017, 3, 3))
                .developer("Nintendo EPD")
                .publisher("Nintendo")
                .description("Explore the wilds of Hyrule any way you like.")
                .imageUrl("https://example.com/botw.jpg")
                .build()
        );
        gameService.addGame(Game.builder()
                .name("rr4rr")
                .platforms(List.of(Platform.NINTENDO_SWITCH))
                .releaseDate(LocalDate.of(2017, 3, 3))
                .developer("Nintendo EPD")
                .publisher("Nintendo")
                .description("Explore the wilds of Hyrule any way you like.")
                .imageUrl("https://example.com/botw.jpg")
                .build()
        );
    }
}
