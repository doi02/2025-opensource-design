package doi.game_review_community.domain.user.service;

import doi.game_review_community.domain.user.User;
import doi.game_review_community.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
        validateDuplicateUsername(user);
        validateDuplicateUserEmail(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUsername(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(existingUser -> {
                    throw new IllegalArgumentException("이미 사용중인 id 입니다: " + user.getUsername());
                });
    }

    private void validateDuplicateUserEmail(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new IllegalArgumentException("이미 사용중인 email 입니다: " + user.getEmail());
                });
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
