package doi.game_review_community.domain.user.repository;

import doi.game_review_community.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long uid);


    Optional<User> findByUsername(String username);


    Optional<User> findByEmail(String email);


    List<User> findAll();


    void save(User user);


    void update(Long uid, User updateParam);


    void updateExp(Long uid, int exp);
}
