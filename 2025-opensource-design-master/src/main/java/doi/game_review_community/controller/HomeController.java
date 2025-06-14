package doi.game_review_community.controller;

import doi.game_review_community.domain.game.Game;
import doi.game_review_community.domain.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GameService gameService;

    @GetMapping("/")
    public String home(Model model) {
        List<Game> hotGames = new ArrayList<>();
        hotGames.addAll(gameService.searchByName("Red Dead Redemption 2"));
        hotGames.addAll(gameService.searchByName("Cyberpunk 2077"));

        List<Game> rankingGames = new ArrayList<>();
        rankingGames.addAll(gameService.searchByName("Elden Ring"));
        rankingGames.addAll(gameService.searchByName("Hades"));

        List<Game> popularGames = new ArrayList<>();
        popularGames.addAll(gameService.searchByName("The Legend of Zelda: Breath of the Wild"));
        popularGames.addAll(gameService.searchByName("Mario Kart World"));

        model.addAttribute("hotGames", hotGames);
        model.addAttribute("rankingGames", rankingGames);
        model.addAttribute("popularGames", popularGames);
        return "index";
    }
}
