package doi.game_review_community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {

        boolean isLoggedIn = false;
        //로그인 인증 제작필요

        model.addAttribute("isLoggedIn", isLoggedIn);
        return "index";
    }
}
