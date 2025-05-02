package doi.game_review_community.domain.user.service;

import doi.game_review_community.domain.user.User;
import doi.game_review_community.domain.user.repository.UserRepository;
import doi.game_review_community.dto.UserRegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long uid) {
        return userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("uid가 \"" + uid + "\"인 회원은 존재하지 않습니다."));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("id가 \"" + username + "\"인 회원은 존재하지 않습니다."));
    }

    @Override
    public Long join(User user) {
        // 중복 회원 검증
        validateDuplicateUsername(user.getUsername());
        validateDuplicateUserEmail(user.getEmail());
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Long join(UserRegistrationDto dto) {
        // 내부의 비밀번호 확인, 중복검사
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 아이디/이메일 중복검사
        validateDuplicateUsername(dto.getUsername());
        validateDuplicateUserEmail(dto.getEmail());

        //엔티티 생성
        User user = new User(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getEmail());
        userRepository.save(user);
        return user.getId();
    }

    private boolean existsUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
    private boolean existsEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private void validateDuplicateUsername(String username) {
        if (existsUsername(username)) {
            throw new IllegalArgumentException("이미 사용중인 id 입니다: " + username);
        }
    }

    private void validateDuplicateUserEmail(String email) {
        if (existsEmail(email)) {
            throw new IllegalArgumentException("이미 사용중인 email 입니다: " + email);
        }
    }
    
    //ajax 전용
    @Override
    public boolean isUsernameAvailable(String username) {
        return !existsUsername(username);
    }
    //ajax 전용
    @Override
    public boolean isEmailAvailable(String email) {
        return !existsEmail(email);
    }

    @Override
    public Long updateUser(Long uid, User updateParam) {
        User findUser = userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("uid가 \"" + uid + "\"인 회원은 존재하지 않습니다."));

        userRepository.update(uid, updateParam);
        return uid;
    }

    @Override
    public void addExperience(Long uid, int exp) {
        User findUser = userRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("uid가 \"" + uid + "\"인 회원은 존재하지 않습니다."));
        userRepository.updateExp(uid, exp);
    }
}
