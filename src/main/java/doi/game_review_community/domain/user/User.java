package doi.game_review_community.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private int level;
    private int exp;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void levelUp(){
        this.level++;
        exp -= 100;
    }

    public void addExp(int points) {
        this.exp += points;
        while(exp >= 100){
            levelUp();
        }
    }



}
