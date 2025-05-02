package doi.game_review_community.domain.user.service;

import doi.game_review_community.domain.user.User;
import doi.game_review_community.dto.UserRegistrationDto;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();


    User findUserById(Long uid);


    User findUserByUsername(String username);


    Long join(User user);

    Long join(UserRegistrationDto dto);

    boolean isUsernameAvailable(String username);

    boolean isEmailAvailable(String email);

    Long updateUser(Long uid ,User updateParam);


    void addExperience(Long uid, int exp);
}
