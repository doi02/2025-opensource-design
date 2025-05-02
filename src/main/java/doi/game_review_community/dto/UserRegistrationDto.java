package doi.game_review_community.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;

@Getter @Setter
public class UserRegistrationDto {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자여야 합니다.")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "아이디는 영문자와 숫자만 사용할 수 있습니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 24, message = "비밀번호는 8~24자여야 합니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "비밀번호는 영문자와 숫자를 모두 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
    private String confirmPassword;

    //signup @Valid DTO검증
    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치해야 합니다.")
    public boolean isPasswordMatching(){
        if (password == null ||  confirmPassword == null)
            return false;
        return password.equals(confirmPassword);
    }
}
