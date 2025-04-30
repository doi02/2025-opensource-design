package doi.game_review_community.domain.user.repository;

import doi.game_review_community.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {
    //임시
    private static final Map<Long, User> store = new ConcurrentHashMap<Long, User>();
    private static AtomicLong sequence = new AtomicLong(0);


    @Override
    public Optional<User> findById(Long uid) {
        return Optional.ofNullable(store.get(uid));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return store.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<User>(store.values());
    }

    @Override
    public void save(User user) {
        user.setId(sequence.incrementAndGet());
        store.put(user.getId(), user);
    }

    @Override
    public void update(Long uid, User updateParam) {
        User findUser = findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("uid가 \"" + uid + "\"인 회원은 존재하지 않습니다."));

        if (updateParam.getUsername() != null) {
            findUser.setUsername(updateParam.getUsername());
        }
        if (updateParam.getEmail() != null) {
            findUser.setEmail(updateParam.getEmail());
        }
        if (updateParam.getPassword() != null) {
            findUser.setPassword(updateParam.getPassword());
        }

    }

    @Override
    public void updateExp(Long uid, int exp) {
        User findUser = findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("uid가 \"" + uid + "\"인 회원은 존재하지 않습니다."));
        if(findUser != null) {
            findUser.addExp(exp);
        }
    }
}
