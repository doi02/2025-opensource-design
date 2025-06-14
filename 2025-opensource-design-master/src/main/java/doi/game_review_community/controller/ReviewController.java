package doi.game_review_community.controller;

import doi.game_review_community.domain.game.service.GameService;
import doi.game_review_community.domain.review.Review;
import doi.game_review_community.dto.ReviewDto;
import doi.game_review_community.domain.review.service.ReviewService;
import doi.game_review_community.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/games")
@RequiredArgsConstructor
public class ReviewController {

    private final UserService userService;
    private final GameService gameService;
    private final ReviewService reviewService;

    @PostMapping("/{gameId}/reviews")
    public String addReview(@PathVariable Long gameId,
                            @Valid @ModelAttribute("newReview") ReviewDto reviewDto,
                            BindingResult bindingResult,
                            Principal principal,
                            RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            redirectAttrs.addFlashAttribute(
                    "org.springframework.validation.BindingResult.newReview", bindingResult);
            redirectAttrs.addFlashAttribute("newReview", reviewDto);
            return "redirect:/games/" + gameId;
        }
        try {
            Long userId = userService
                    .findUserByUsername(principal.getName()).getId();
            reviewService.addReview(gameId, userId, reviewDto);
        } catch (IllegalStateException e) {
            // 중복 에러
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttrs.addFlashAttribute("newReview", reviewDto);
            return "redirect:/games/" + gameId;
        }
        return "redirect:/games/" + gameId;
    }

    @GetMapping("/{gameId}/reviews/{reviewId}/edit")
    public String editReview(@PathVariable Long gameId,
                             @PathVariable Long reviewId,
                             Model model,
                             Principal principal
                             ){
        Review existing= reviewService.findByReviewId(reviewId);
        if (!existing.getAuthor().getUsername().equals(principal.getName())) {
            throw new AccessDeniedException("본인의 리뷰만 수정할 수 있습니다.");
        }

        ReviewDto reviewDto = ReviewDto.builder()
                .content(existing.getContent())
            .graphicScore(existing.getGraphicScore())
                .storyScore(existing.getStoryScore())
                .gameplayScore(existing.getGameplayScore())
                .soundScore(existing.getSoundScore())
                .build();
        model.addAttribute("game", gameService.findGameById(gameId));
        model.addAttribute("reviewDto", reviewDto);
        return "games/edit";
    }

    //리뷰수정
    @PutMapping("/{gameId}/reviews/{reviewId}")
    public String updateReview(@PathVariable Long gameId,
                               @PathVariable Long reviewId,
                               @Valid ReviewDto reviewDto,
                               BindingResult bindingResult,
                               Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/games/" + gameId;
        }
        Review existing = reviewService.findByReviewId(reviewId);
        if (!existing.getAuthor().getUsername().equals(principal.getName())){
            throw new AccessDeniedException("본인의 리뷰만 수정할 수 있습니다.");
        }
        reviewService.updateReview(reviewId, reviewDto);
        return "redirect:/games/" + gameId;
    }

    //리뷰삭제
    @DeleteMapping("/{gameId}/reviews/{reviewId}")
    public String deleteReview(@PathVariable Long gameId,
                               @PathVariable Long reviewId,
                               Principal principal) {
        Review existing = reviewService.findByReviewId(reviewId);
        if (!existing.getAuthor().getUsername().equals(principal.getName())){
            throw new AccessDeniedException("본인의 리뷰만 삭제할 수 있습니다.");
        }
        reviewService.deleteReview(reviewId);
        return "redirect:/games/" + gameId;
    }

    //리뷰좋아요
    @PostMapping("/{gameId}/reviews/{reviewId}/like")
    public String likeReview(@PathVariable Long gameId,
                             @PathVariable Long reviewId,
                             Principal principal) {
        Long userId = userService.findUserByUsername(principal.getName()).getId();
        reviewService.toggleLikeReview(reviewId, userId);
        return "redirect:/games/" + gameId;
    }

    //리뷰싫어요
    @PostMapping("/{gameId}/reviews/{reviewId}/dislike")
    public String dislikeReview(@PathVariable Long gameId,
                                @PathVariable Long reviewId,
                                Principal principal) {
        Long userId = userService.findUserByUsername(principal.getName()).getId();
        reviewService.toggleDislikeReview(reviewId, userId);
        return "redirect:/games/" + gameId;
    }
}