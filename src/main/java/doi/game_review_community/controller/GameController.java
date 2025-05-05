package doi.game_review_community.controller;

import doi.game_review_community.domain.game.Game;
import doi.game_review_community.domain.game.service.GameService;
import doi.game_review_community.domain.review.service.ReviewService;
import doi.game_review_community.domain.user.service.UserService;
import doi.game_review_community.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final ReviewService reviewService;

    // 전체 목록
    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("games", gameService.findAllGames());
        return "games/list";
    }

    // 게임상세페이지(게임정보, 게임리뷰)
    @GetMapping("/{id}")
    public String gameDetail(@PathVariable Long id,
                             Model model,
                             @ModelAttribute("newReview") ReviewDto newReview) {
        model.addAttribute("game",    gameService.findGameById(id));
        model.addAttribute("reviews", reviewService.findByGameId(id));
        if (!model.containsAttribute("newReview")) {
            model.addAttribute("newReview", new ReviewDto());
        }
        return "games/detail";
    }

    // 검색
    @GetMapping("/search")
    public String search(
            @RequestParam(value = "q", required = false) String keyword,
            Model model
    ) {
        List<Game> results = gameService.searchByName(keyword);
        model.addAttribute("games", results);
        model.addAttribute("keyword", keyword);
        return "games/list";
    }
}
