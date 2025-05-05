package doi.game_review_community.controller;

import doi.game_review_community.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import doi.game_review_community.domain.user.service.UserService;
import doi.game_review_community.dto.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //가입폼
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "user/signup";
    }

    //가입 처리
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") UserRegistrationDto dto,
                         BindingResult binding, Model model) {
        if (binding.hasErrors()) {
            return "user/signup";
        }

        try{
            //회원가입 로직
            userService.join(dto);
        } catch (IllegalArgumentException e){
            // 중복, 비밀번호 재입력 불일치
            model.addAttribute("error", e.getMessage());
            return "user/signup";
        }
        //가입성공 리다이렉트
        return "redirect:/user/login";
    }


    //ajax
    @GetMapping("/check-username")
    @ResponseBody
    public boolean checkUsername(@RequestParam("username") String username) {
        return userService.isUsernameAvailable(username);
    }

    //ajax
    @GetMapping("/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        return userService.isEmailAvailable(email);
    }

    @GetMapping("/myprofile")
    public String viewMyProfile(Model model, Principal principal) {
        //유저명은  Principal로 조회
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/myprofile";
    }
}
